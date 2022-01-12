package com.example.leprojetdelesjeux;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.InetAddresses;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multiplayer extends AppCompatActivity {

    TextView infosConnexion;
    Button onOffButton, discoverButton, startButton;
    ListView connexionsListView;

    WifiP2pManager manager;
    WifiP2pManager.Channel channel;

    BroadcastReceiver receiver;
    IntentFilter intentFilter;

    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;

    Socket socket;

    ServerClass serverClass;
    ClientClass clientClass;
    boolean isHost;

    //analyse du résultat reçu par l'activité lancée
    ActivityResultLauncher<Intent> startActivityForResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK) {
                if(result.getData() != null && result.getData().getIntArrayExtra(ShakeIt.RESULT) != null) {
                    //infosConnexion.setText(result.getData().getIntArrayExtra(ShakeIt.RESULT));
                    for(int i = 0; i < result.getData().getIntArrayExtra(ShakeIt.RESULT).length; i++) {
                        System.out.println("Jeu "+i+" : "+result.getData().getIntArrayExtra(ShakeIt.RESULT)[i]);
                    }
                    if(!isHost) {
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                int[] resultatsObtenus = result.getData().getIntArrayExtra(ShakeIt.RESULT);
                                String results = "shake:"+resultatsObtenus[0]+";light:"+resultatsObtenus[1]+";scream:"+resultatsObtenus[2]+";justePrix:"+resultatsObtenus[3];
                                clientClass.write(results.getBytes());
                            }
                        });
                    }
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        initialisation();
        exqListener();

    }

    private void exqListener() {
        onOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivityForResult(intent, 1);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if(isHost) {
                            serverClass.write("startGame".getBytes());
                            Intent intent = new Intent(getApplicationContext(), ShakeIt.class);
                            startActivityForResults.launch(intent);
                        }
                        else if(!isHost) {
                            clientClass.write("startGame".getBytes());
                            Intent intent = new Intent(getApplicationContext(), ShakeIt.class);
                            startActivityForResults.launch(intent);
                        }
                    }
                });
            }
        });

        discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        infosConnexion.setText("Découverte démarrée");
                    }

                    @Override
                    public void onFailure(int i) {
                        infosConnexion.setText("Découverte non démarrée");
                    }
                });
            }
        });

        connexionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final WifiP2pDevice device = deviceArray[position];
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                manager.connect(channel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        infosConnexion.setText("Connecté : "+device.deviceAddress);
                    }

                    @Override
                    public void onFailure(int i) {
                        infosConnexion.setText("Non connecté");
                    }
                });
            }
        });
    }

    private void initialisation() {
        infosConnexion = findViewById(R.id.infosConnexion);
        onOffButton = findViewById(R.id.onOffButton);
        discoverButton = findViewById(R.id.discoverButton);
        connexionsListView = findViewById(R.id.connexionsListView);
        startButton = findViewById(R.id.connectButton);
        startButton.setEnabled(false);
        startButton.setBackgroundColor(Color.RED);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
    }

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
            if(!wifiP2pDeviceList.equals(peers)) {
                peers.clear();
                peers.addAll(wifiP2pDeviceList.getDeviceList());

                deviceNameArray = new String[wifiP2pDeviceList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[wifiP2pDeviceList.getDeviceList().size()];

                int index = 0;

                for(WifiP2pDevice device : wifiP2pDeviceList.getDeviceList()){
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                    index++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                connexionsListView.setAdapter(adapter);

                if(peers.size() == 0){
                    infosConnexion.setText("Aucun appareil trouvé");
                    return;
                }
            }
        }
    };

    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            final InetAddress groupOwnerAddress = wifiP2pInfo.groupOwnerAddress;
            if(wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
                infosConnexion.setText("Hôte");
                isHost = true;
                serverClass = new ServerClass();
                serverClass.start();
                startButton.setBackgroundColor(Color.GREEN);
                startButton.setEnabled(true);
                //String msg = "lance l'activité";
                //sendMessage(msg);
                //serverClass.write(msg.getBytes());
            }
            else {
                infosConnexion.setText("Client");
                isHost = false;
                clientClass = new ClientClass(groupOwnerAddress);
                clientClass.start();
                startButton.setBackgroundColor(Color.GREEN);
                startButton.setEnabled(true);
                //String msg = "toi aussi lance l'activité";
                //sendMessage(msg);
                //clientClass.write(msg.getBytes());
            }
        }
    };

    /*private void sendMessage(String msgToSend) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(msgToSend != null && isHost) {
                    System.out.println("Bytes : "+msgToSend.getBytes());
                    serverClass.write(msgToSend.getBytes());
                }
                else if(msgToSend != null && !isHost) {
                    System.out.println("Bytes : "+msgToSend.getBytes());
                    clientClass.write(msgToSend.getBytes());
                }
            }
        });
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    public class ServerClass extends Thread {
        ServerSocket serverSocket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];
                    int bytes;

                    while(socket != null) {
                        try {
                            bytes = inputStream.read(buffer);
                            if(bytes > 0) {
                                int finalBytes = bytes;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String tempMsg = new String(buffer, 0, finalBytes);
                                        System.out.println("Temp msg : !"+tempMsg+"!");
                                        if(tempMsg.equals("startGame")) {
                                            System.out.println("Je lance l'activité");
                                            Intent intent = new Intent(getApplicationContext(), ShakeIt.class);
                                            startActivityForResults.launch(intent);
                                        }
                                        else { //sinon, réception des résultats
                                            infosConnexion.setText(tempMsg);
                                        }
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public class ClientClass extends Thread {
        String hostAdd;
        private InputStream inputStream;
        private OutputStream outputStream;

        public ClientClass(InetAddress hostAddress) {
            hostAdd = hostAddress.getHostAddress();
            socket = new Socket();
        }

        public void write(byte[] bytes) {
            try {
                this.outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(outputStream != null){
                System.out.println("outputStream n'est pas null");
            }
            else {
                System.out.println("outputStream est null");
            }

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];
                    int bytes;

                    while(socket != null) {
                        try {
                            bytes = inputStream.read(buffer);
                            if(bytes>0) {
                                int finalBytes = bytes;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run(){
                                        String tempMsg = new String(buffer, 0, finalBytes);
                                        System.out.println("Temp msg : !"+tempMsg+"! !startGame!");
                                        if(tempMsg.equals("startGame")) {
                                            System.out.println("Je lance l'activité");
                                            Intent intent = new Intent(getApplicationContext(), ShakeIt.class);
                                            startActivityForResults.launch(intent);
                                        }
                                        else { //sinon, réception des résultats
                                            System.out.println("Je n'ai pas lancé l'activité");
                                        }
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
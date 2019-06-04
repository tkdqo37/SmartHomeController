package com.example.bt_ko

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import kotlinx.android.synthetic.main.activity_main.*

lateinit var bt: BluetoothSPP

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt = BluetoothSPP(this)

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(applicationContext, "Bluetooth is not available", Toast.LENGTH_SHORT).show()
            finish()
        }

        bt.setOnDataReceivedListener { data, message ->
            //데이터 수신
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }

        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener { //연결됐을 때
            override fun onDeviceConnected(name: String, address: String) {
                Toast.makeText(applicationContext, "$name$address 에 \n 연결 되었습니다. ", Toast.LENGTH_SHORT).show()

            }

            override fun onDeviceDisconnected() { //연결해제
                Toast.makeText(applicationContext, "연결이 해제 되었습니다", Toast.LENGTH_SHORT).show()
            }

            override fun onDeviceConnectionFailed() { //연결실패
                //Toast.makeText(applicationContext, "연결에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        //val btnConnect = findViewById(R.id.btnConnect) as Button //연결시도
        btnConnect.setOnClickListener(View.OnClickListener {

            val intent = Intent(applicationContext, DeviceList::class.java)
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)

        })
        btnDisconnect.setOnClickListener {
            if (bt.serviceState == BluetoothState.STATE_CONNECTED) {
                bt.disconnect()

            } else {
                Toast.makeText(applicationContext, "연결되어있는 블루투스 없음.", Toast.LENGTH_SHORT).show()
            }

        }

        btnLedOff.setOnClickListener(View.OnClickListener { bt.send("0", true) })
        btnLedOn.setOnClickListener(View.OnClickListener { bt.send("1", true) })
        btnCloseDoor.setOnClickListener(View.OnClickListener { bt.send("2", true) })
        btnOpenDoor.setOnClickListener(View.OnClickListener { bt.send("3", true) })
        btnRed.setOnClickListener(View.OnClickListener { bt.send("4", true) })
        btnGreen.setOnClickListener(View.OnClickListener { bt.send("5", true) })
        btnBlue.setOnClickListener(View.OnClickListener { bt.send("6", true) })
    }

    override fun onDestroy() {
        super.onDestroy()
        bt.stopService() //블루투스 중지
    }




    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data!!)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)

            } else {
                Toast.makeText(applicationContext, "블루투스에 연결되지 않았습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
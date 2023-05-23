package com.example.mygmap2

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.mygmap2.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var googleMap: GoogleMap
    var loc = LatLng(37.554752,126.970631)

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val permissions= arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION)

    val gpsSettingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(checkGPSProvider()){
            getLastLocation()
        }else{
            setCurrentLocation(loc)
        }
    }

    val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        permissions->when{
            permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION,false)||
                    permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION,false)->{
                        getLastLocation()
                    }
        else->{
            setCurrentLocation(loc)
        }
        }
    }

    private fun checkGPSProvider(): Boolean{
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }

    private fun showGPSSetting(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 허용하겠습니까?")
        builder.setPositiveButton("설정", {
            dialog, id->
            val GpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            gpsSettingsLauncher.launch(GpsSettingIntent)
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener{
            dialog, id->
            dialog.dismiss()
            setCurrentLocation(loc)
        })
        builder.create().show()
    }

    private fun showPermissionRequestDlg(){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 제공")
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "기기의 위치를 제공하도록 설정하시겠습니까?")
        builder.setPositiveButton("설정", {
                dialog, id->
            locationPermissionRequest.launch(permissions)
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener{
                dialog, id->
            dialog.dismiss()
            setCurrentLocation(loc)
        })
        builder.create().show()
    }

    private fun checkFineLocationPermission():Boolean{
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
    }

    private fun checkCoarseLocationPermission():Boolean{
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initmap()
    }

    private fun initmap() {
        val mapFragment=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            initLocation()
        }
    }

    private fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    fun setCurrentLocation(location:LatLng){
        val option = MarkerOptions()
        option.position(loc)
        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        googleMap.addMarker(option)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,16.0f))
    }

    private fun getLastLocation() {
        when{
            checkFineLocationPermission()->{
                if(!checkGPSProvider()){
                    showGPSSetting()
                }else{
                    fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,
                        object : CancellationToken(){
                            override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                                return CancellationTokenSource().token
                            }

                            override fun isCancellationRequested(): Boolean {
                                return false
                            }
                        }).addOnSuccessListener {
                            if(it!=null){
                                loc= LatLng(it.latitude, it.longitude)
                            }
                        setCurrentLocation(loc)
                    }
                    // currentlocation이 아닌 lastlocation 가지고 오고 싶을 때
//                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
//                        if(it!=null){
//                            loc = LatLng(it.latitude, it.longitude)
//                        }
//                        setCurrentLocation(loc)
//                    }
                }
            }

            checkCoarseLocationPermission()->{
                fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    object : CancellationToken(){
                        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                            return CancellationTokenSource().token
                        }

                        override fun isCancellationRequested(): Boolean {
                            return false
                        }
                    }).addOnSuccessListener {
                    if(it!=null){
                        loc=LatLng(it.latitude, it.longitude)
                    }
                    setCurrentLocation(loc)
//                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
//                        if(it!=null){
//                            loc = LatLng(it.latitude, it.longitude)
//                        }
//                        setCurrentLocation(loc)
//                    }
                }
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)->{
                showPermissionRequestDlg()
            }

            else->{
                locationPermissionRequest.launch(permissions)
            }
        }
    }
}
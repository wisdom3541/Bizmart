package com.example.bizmart

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bizmart.BuildConfig.MAPS_API_KEY
import com.example.bizmart.data.GeocodingResponse
import com.example.bizmart.data.Values
import com.example.bizmart.databinding.ActivityMapsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
import kotlin.properties.Delegates


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var address :String
    private lateinit var addy: String
    private lateinit var fAddy: String
    private lateinit var a: List<Values>
    private var lat: Double = 0.0
    private var lng: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // editAddress(address)


        // val quotesApi = RetrofitHelper.getInstance().create(GeocodingApi::class.java)

        // launching a new coroutine
//        GlobalScope.launch {
//            val result = quotesApi.reposList(fAddy)
//            if (result != null) {
//                // Checking the results
//
//                val value : Values? =  result.body()?.results?.get(0)
//               lat = value?.geometry?.location?.lat!!
//               lng = value.geometry.location.lng
//
//                Log.d(
//                    "Size: ", value.toString() + lat + lng
//
//                )
//            }
//
//        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //  MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST, this)


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {


        mMap = googleMap
//
//        val lat = geoPoint?.latitude
//        val lng = geoPoint?.longitude
        // Add a marker in Sydney and move the camera
        lat = intent?.extras?.getDouble("lat").toString().toDouble()
        lng = intent?.extras?.getDouble("lng").toString().toDouble()
        address = intent?.extras?.getString("address").toString()

        Log.d("Tag : ", lat.toString() + lng)
        val sydney = LatLng(lat, lng)
        mMap.addMarker(MarkerOptions().position(sydney).title(address))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(25F));
        //Display traffic
        googleMap.isTrafficEnabled = true;
        // Set the map type to Hybrid.
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID;
    }


//    private fun editAddress(ad: String) {
//        addy = ad.replace("\\s+".toRegex(), "%20").toLowerCase();
//        addy = addy.replace("\\.".toRegex(), "").toLowerCase();
//        addy = addy.replace(",".toRegex(), "").toLowerCase();
//        Log.d("Addy :", addy)
//
//
//        fAddy = "maps/api/geocode/json?address=$addy&key=${MAPS_API_KEY}"
//
//        Log.d("Addy :", fAddy)
//    }


}


//// Set the map coordinates to Kyoto Japan.
//LatLng kyoto = new LatLng(35.00116, 135.7681);
//// Set the map type to Hybrid.
//googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//// Add a marker on the map coordinates.
//googleMap.addMarker(new MarkerOptions()
//.position(kyoto)
//.title("Kyoto"));
//// Move the camera to the map coordinates and zoom in closer.
//googleMap.moveCamera(CameraUpdateFactory.newLatLng(kyoto));
//googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
//// Display traffic.
//googleMap.setTrafficEnabled(true);
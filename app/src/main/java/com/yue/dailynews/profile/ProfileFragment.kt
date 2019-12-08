package com.yue.dailynews.profile


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import com.yue.dailynews.R
import com.yue.dailynews.common.TinBasicFragment
import com.yue.dailynews.common.Util
import com.yue.dailynews.tin.TinGalleryFragment
import kotlinx.android.synthetic.*
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.app.Activity
import com.yue.dailynews.MainActivity
import com.yue.dailynews.common.TinBasicActivity
import com.yue.dailynews.common.TinFragmentManager


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : TinBasicFragment(){
    private lateinit var cleanCache : TextView
    private lateinit var usNew : TextView
    private lateinit var usSource : TextView
    private lateinit var geNews : TextView
    private lateinit var geSource: TextView
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Util.setAppLocale(Util.language,resources)
        var mtinFragmentManager: TinFragmentManager

        mtinFragmentManager = context as TinFragmentManager
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        cleanCache = view.findViewById(R.id.ClearCache)
        usNew = view.findViewById(R.id.usNews)
        usSource = view.findViewById(R.id.usSource)
        geNews = view.findViewById(R.id.germanNews)
        geSource = view.findViewById(R.id.germanSource)

        geNews.setOnClickListener{
            TinGalleryFragment.country = "de"
            val bundle = Bundle()
            mtinFragmentManager.startActivityWithBundle(MainActivity::class.java as Class<Any>, true, bundle)
            Toast.makeText(this.activity, "Change to German News", Toast.LENGTH_SHORT).show()
        }

        usNew.setOnClickListener{
            TinGalleryFragment.country = "us"
            val bundle = Bundle()
            mtinFragmentManager.startActivityWithBundle(MainActivity::class.java as Class<Any>, true, bundle)
            Toast.makeText(this.activity, "Change to English News", Toast.LENGTH_SHORT).show()
        }

        usSource.setOnClickListener{
            Util.language = "us"
            val bundle = Bundle()
            mtinFragmentManager.startActivityWithBundle(MainActivity::class.java as Class<Any>, true, bundle)
            Toast.makeText(this.activity, "Change to German Language", Toast.LENGTH_SHORT).show()
        }

        geSource.setOnClickListener{
            Util.language = "de"
            val bundle = Bundle()
            mtinFragmentManager.startActivityWithBundle(MainActivity::class.java as Class<Any>, true, bundle)
            Toast.makeText(this.activity, "Zur deutschen Sprache wechseln", Toast.LENGTH_SHORT).show()
        }

            firebaseAuth = FirebaseAuth.getInstance()
            Log.d("profile", "onCreate called-Auth")

            firebaseDatabase = FirebaseDatabase.getInstance()
            Log.d("profile", "onCreate called-db")
            firebaseRef = firebaseDatabase.getReference()
            val opRef : DatabaseReference = firebaseDatabase.getReference("dailyNews/Opeartions/${firebaseAuth.currentUser?.uid}")

            opRef.addValueEventListener(object: ValueEventListener{

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@ProfileFragment.activity,
                        "Clean Cache Successful:$error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    cleanCache.setOnClickListener {
                        for (newsSnapshot in dataSnapshot.children) {
                            newsSnapshot.ref.removeValue()
                        }
                        Toast.makeText(activity, "Clean Cache Successful", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(): ProfileFragment{
            var args = Bundle()
            var fragment = ProfileFragment()
            fragment.setArguments(args)
            return fragment
        }
    }


}

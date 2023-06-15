package com.eh.saldofacil

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.eh.saldofacil.adapter.BilheteAdapter
import com.eh.saldofacil.databinding.FragmentListaBinding
import com.eh.saldofacil.model.Bilhete

class ListaFragment : Fragment() {

    private lateinit var binding: FragmentListaBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListaBinding.inflate(layoutInflater, container, false)
        val adapter = BilheteAdapter()
        binding.recyclerBilhete.adapter = adapter
        binding.recyclerBilhete.layoutManager = LinearLayoutManager(context)
        binding.recyclerBilhete.setHasFixedSize(true)

        mainViewModel.cookies = activity?.intent?.getSerializableExtra("cookies") as HashMap<String, String>

        mainViewModel.mostrarCartoes()

        mainViewModel.bilhetes.observe(viewLifecycleOwner){
            if (it != null){
                adapter.setLista(it)
            }
            Log.d("Opa", it.toString())
        }

        return binding.root
    }

}
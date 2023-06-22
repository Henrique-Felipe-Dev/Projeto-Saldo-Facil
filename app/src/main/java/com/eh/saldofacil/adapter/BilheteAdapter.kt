package com.eh.saldofacil.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eh.saldofacil.databinding.CardLayoutBinding
import com.eh.saldofacil.model.Bilhete

class BilheteAdapter: Adapter<BilheteAdapter.BilheteViewHolder>() {

    private var listBilhete = emptyList<Bilhete>()

    class BilheteViewHolder(val binding: CardLayoutBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BilheteViewHolder {
        return BilheteViewHolder(
            CardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BilheteViewHolder, position: Int) {
        val bilhete = listBilhete[position]

        holder.binding.textNumero.text = bilhete.numero

        holder.binding.buttonSaldo.setOnClickListener {
            showAlertSaldo(bilhete, holder.itemView.context)
        }

        /*
        if(bilhete.situacao != "EMITIDO"){
            holder.binding.buttonSaldo.isEnabled = false
        }

         */

    }

    override fun getItemCount(): Int {
        return listBilhete.size
    }

    fun setLista(listBilhete: List<Bilhete>) {
        this.listBilhete = listBilhete
        notifyDataSetChanged()
    }

    fun showAlertSaldo(bilhete: Bilhete, context: Context) {

        if(bilhete.situacao != "EMITIDO") {
            AlertDialog.Builder(context)
                .setTitle("Não foi possível consultar o saldo.")
                .setMessage("MOTIVO: Cartão ${bilhete.situacao}")
                .setPositiveButton("Ok") { _,_ ->

                }
                .create()
                .show()
        }else if (bilhete.saldo.isBlank()) {
            AlertDialog.Builder(context)
                .setTitle("Não foi possível consultar o saldo.")
                .setMessage("Bilhete antigo ou erro desconhecido...")
                .setPositiveButton("Ok") { _,_ ->

                }
                .create()
                .show()
        }else{
            AlertDialog.Builder(context)
                .setTitle("Saldo")
                .setMessage("Número do Cartão: ${bilhete.numero}\nSeu saldo é: ${bilhete.saldo}")
                .setPositiveButton("Ok") { _,_ ->

                }
                .create()
                .show()
        }

    }

}
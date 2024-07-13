package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.renzo.myapplication.R
import models.EnfrentamientoModel

class EquiposAdapter(private var lstProductos: List<EnfrentamientoModel>) :
    RecyclerView.Adapter<EquiposAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvID: TextView = itemView.findViewById(R.id.tvID)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_enfrentamiento, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lstProductos[position]
        holder.tvID.text = producto.id
        holder.tvCantidad.text = producto.quantity
        holder.tvDescripcion.text = producto.description
        holder.tvPrecio.text = producto.price
    }

    override fun getItemCount(): Int = lstProductos.size

    fun actualizarProductos(nuevosProductos: List<EnfrentamientoModel>) {
        lstProductos = nuevosProductos
        notifyDataSetChanged()
    }
}
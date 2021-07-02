package net.rafaeltoledo.filasp.ui.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.rafaeltoledo.filasp.R
import net.rafaeltoledo.filasp.api.VaccinationPlace
import net.rafaeltoledo.filasp.databinding.ItemPlaceBinding

class ListingAdapter(private val places: List<VaccinationPlace>) : RecyclerView.Adapter<ListingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListingViewHolder(ItemPlaceBinding.bind(inflater.inflate(R.layout.item_place, parent, false)))
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount() = places.size
}

class ListingViewHolder(private val item: ItemPlaceBinding) : RecyclerView.ViewHolder(item.root) {

    fun bind(place: VaccinationPlace) {
        item.placeTitle.text = place.name
    }
}
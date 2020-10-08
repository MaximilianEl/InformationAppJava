package com.example.informationappjava.ui.institute;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

  private List<Person> personList;
  private OnItemClickListener listener;

  public interface OnItemClickListener {

    void onItemClick(int position);
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.listener = listener;
  }

  public PersonAdapter(List<Person> personList) {
    this.personList = personList;
  }


  @NonNull
  @NotNull
  @Override
  public PersonViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent,
      int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.institute_data, parent, false);
    return new PersonViewHolder(view, listener);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull PersonViewHolder holder, int position) {

    Person person = personList.get(position);
    holder.docentName.setText(person.getDocentName());
    holder.docentDescription.setText(person.getDocentDesc());
    holder.docentPic.setImageResource(person.getPersonImage());
  }

  @Override
  public int getItemCount() {
    return personList.size();
  }

  public static class PersonViewHolder extends RecyclerView.ViewHolder {

    public TextView docentName;
    public TextView docentDescription;
    public RoundedImageView docentPic;
    public CardView cardView;

    public PersonViewHolder(@NonNull @NotNull View itemView, OnItemClickListener listener) {
      super(itemView);

      docentName = itemView.findViewById(R.id.ins_name);
      docentDescription = itemView.findViewById(R.id.ins_item_description);
      docentPic = itemView.findViewById(R.id.ins_images);

    }
  }
}
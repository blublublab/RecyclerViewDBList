package com.demo.listoftheday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter  extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private ArrayList<Note> notes;
    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }
    public interface OnNoteListener{
        void onNoteLongClick(int position);
    }
    OnNoteListener onNoteListener;

    public void setOnNoteListener(OnNoteListener onNoteListener) {
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    Note note = notes.get(position);
    holder.textViewTitle.setText(note.getTitle());
    holder.textViewDayOfTheWeek.setText(note.getDayOfTheWeek());
    holder.textViewDescription.setText(note.getDescription());
    int colorId;
    int priority = note.getPriority();
    switch(priority) {
        case 1:
            colorId = holder.itemView.getResources().getColor(android.R.color.holo_red_light);
            break;
        case 2:
            colorId = holder.itemView.getResources().getColor(android.R.color.holo_orange_light);
            break;

        default:
            colorId = holder.itemView.getResources().getColor(android.R.color.holo_green_light);
            break;

    }

    holder.textViewTitle.setBackgroundColor(colorId);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        private final TextView textViewDescription;
        private final TextView textViewTitle;
        private final TextView textViewDayOfTheWeek;
        private final ConstraintLayout constraintLayoutNote;
        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onNoteListener.onNoteLongClick(getAdapterPosition());
                    return true;
                }
            });


            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription  = itemView.findViewById(R.id.textViewDescription);
            textViewDayOfTheWeek = itemView.findViewById(R.id.textViewDayOfTheWeek);
            constraintLayoutNote = itemView.findViewById(R.id.constraintLayoutNote);

        }


    }
}

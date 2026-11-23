package edu.uga.cs.ugarideshare;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.CardHolder>
{
    private final Context context;
    private final List<RideShareData.Request> requestList;
    private final List<RideShareData.Offer> offerList;

    public static int offers;

    public CardRecyclerAdapter(Context context, List<RideShareData.Request> rideList, String offers) {
        this.context = context;
        this.requestList= rideList;
        this.offers = Integer.parseInt(offers);
    }

    public CardRecyclerAdapter(Context context, List<RideShareData.Offer> offerList, int offers) {
        this.context = context;
        this.offerList= offerList;
        this.offers = offers;
    }



    public static class CardHolder extends RecyclerView.ViewHolder {
        //Requires enough variables for both card types
        public TextView value1;
        public TextView value2;
        public TextView value3;

        public CardHolder(View itemView) {
            super(itemView);
            if (offers == 0) {
                value1 = itemView.findViewById(R.id.requestValue1);
                value2 = itemView.findViewById(R.id.requestValue2);
                value3 = itemView.findViewById(R.id.requestValue3);
            } else {
                value1 = itemView.findViewById(R.id.offerValue1);
                value2 = itemView.findViewById(R.id.offerValue2);
                value3 = itemView.findViewById(R.id.offerValue3);
            }

        }
    }

    /**
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (offers == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_card, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_card, parent, false);
        }

        return new CardHolder(view);
    }

    /**
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        //StateQuizData.Quiz quiz = quizList.get(position);
//        String quizDateTime = quiz.getQuizDate();
//        String datePart;
//        String timePart;
//        if (quizDateTime != null && quizDateTime.contains(" ")) {
//            String[] parts = quizDateTime.split(" ");
//            datePart = parts[0];
//            timePart = parts.length > 1 ? parts[1] : "";
//        } else {
//            datePart = quizDateTime;
//            timePart = "";
//        }
//        holder.quizValue1.setText("Date: " + datePart);
//        holder.quizValue2.setText("Time: " + timePart);
//        holder.quizValue3.setVisibility(View.VISIBLE);
//        holder.quizValue3.setText("Score: " + quiz.getCorrectAnswers() + " / 6");

        //TODO: Get values from the database

        if (offers == 0) {
            holder.value1.setText("Request test 1");
            holder.value2.setText("Request test 2");
            holder.value3.setText("Request test 3");
        } else {
            holder.value1.setText("Offer test 1");
            holder.value2.setText("Offer test 2");
            holder.value3.setText("Offer test 3");
        }
    }

    @Override
    public int getItemCount() {
        if (offers == 0) {
            return offerList != null ? offerList.size() : 0;
        } else {
            return requestList != null ? requestList.size() : 0;
        }

    }
}
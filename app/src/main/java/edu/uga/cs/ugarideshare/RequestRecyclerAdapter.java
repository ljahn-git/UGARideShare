package edu.uga.cs.ugarideshare;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequestRecyclerAdapter extends RecyclerView.Adapter<RequestRecyclerAdapter.RequestHolder>
{
    private final Context context;
    private final List<RideShareData.Request> requestList;

    public RequestRecyclerAdapter(Context context, List<RideShareData.Request> rideList) {
        this.context = context;
        this.requestList= rideList;
    }


    public static class RequestHolder extends RecyclerView.ViewHolder {
        //Requires enough variables for both card types
        public TextView value1;
        public TextView value2;
        public TextView value3;

        public RequestHolder(View itemView) {
            super(itemView);
            value1 = itemView.findViewById(R.id.requestValue1);
            value2 = itemView.findViewById(R.id.requestValue2);
            value3 = itemView.findViewById(R.id.requestValue3);

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
    public RequestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_card, parent, false);

        return new RequestHolder(view);
    }

    /**
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RequestHolder holder, int position) {
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

        holder.value1.setText("Request test 1");
        holder.value2.setText("Request test 2");
        holder.value3.setText("Request test 3");
    }

    @Override
    public int getItemCount() {
        return requestList != null ? requestList.size() : 0;
    }
}
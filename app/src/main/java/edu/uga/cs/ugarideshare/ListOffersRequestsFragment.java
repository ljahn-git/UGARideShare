package edu.uga.cs.ugarideshare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOffersRequestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOffersRequestsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private RecyclerView recyclerView;
    private OfferRecyclerAdapter offerRecyclerAdapter;
    private RequestRecyclerAdapter requestRecyclerAdapter;

    private List<RideShareData.Request> requestList;
    private List<RideShareData.Offer> offerList;

    private int offers;

    public ListOffersRequestsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListOffersRequestsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOffersRequestsFragment newInstance(int offers) {
        ListOffersRequestsFragment fragment = new ListOffersRequestsFragment();
        Bundle args = new Bundle();
        args.putInt("offers", offers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            offers = getArguments().getInt("offers");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_offers_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //TODO: retrieve data based on value of offers and pass data (along with offers) to CardRecyclerAdapter object

        if (offers == 0) {
            requestRecyclerAdapter = new RequestRecyclerAdapter(requireContext(), requestList);
            recyclerView.setAdapter(requestRecyclerAdapter);
        } else {
            offerRecyclerAdapter = new OfferRecyclerAdapter(requireContext(), offerList);
            recyclerView.setAdapter(offerRecyclerAdapter);
        }
    }

    public void saveNewRideOffer(String startingPoint, String endingPoint, String date) {

    }
}
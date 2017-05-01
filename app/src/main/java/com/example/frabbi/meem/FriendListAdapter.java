package com.example.frabbi.meem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by imm on 4/24/2017.
 */

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Object> items;
    private final int label_type = 1;
    private final int notification_type = 2;
    private final int request_type = 3;
    private final int friends_type = 4;
    private final int search_type = 5;
    private ClickCallback mCallback;


    public FriendListAdapter(Context mContext, ArrayList<Object> items, ClickCallback callback) {
        this.mContext = mContext;
        this.items = items;
        mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == friends_type) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.friends_in_circle_list, parent, false);
            return new FriendViewHolder(view);
        } else if (viewType == label_type) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.label_view, parent, false);
            return new LabelViewHolder(view);
        } else if (viewType == request_type) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.friend_request_list, parent, false);
            return new RequestsViewHolder(view);
        } else if (viewType == notification_type) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.notification_list, parent, false);
            return new NotificationViewHolder(view);
        }
        else if (viewType == search_type) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.search_result_list, parent, false);
            return new SearchResultViewHolder(view);
        }
        return null;
    }

    public void addItems(ArrayList<User> arraylist) {
        for (User o : arraylist) {
            items.add(0, o);

        }
        notifyDataSetChanged();
    }

    public  void addLabel(Label label) {
        items.add(0, label);
        notifyDataSetChanged();
    }

    public void removeItems(int size) {
        for (int i = 0; i < size; i++) {
            items.remove(0);

        }
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        if (viewType == friends_type) {
            FriendViewHolder fh = (FriendViewHolder) holder;
            User user = (User) items.get(position);
            fh.friendName.setText(user.getName());
        }
        else if (viewType == label_type) {
            LabelViewHolder lh = (LabelViewHolder) holder;
            Label label = (Label) items.get(position);
            lh.LabelName.setText(label.getLabel());
        }
        else if (viewType == request_type) {
            RequestsViewHolder rh = (RequestsViewHolder) holder;
            User r = (User) items.get(position);
            String text = r.getName() + " sent you a request.";
            rh.requ.setText(text);
        }
        else if (viewType == notification_type) {
            NotificationViewHolder lh = (NotificationViewHolder) holder;
            Notification no = (Notification) items.get(position);
            lh.confirmation.setText(no.getMessage());
        }
        else if (viewType == search_type) {
            SearchResultViewHolder lh = (SearchResultViewHolder) holder;
            User no = (User) items.get(position);
            lh.idToSearch.setText(no.getName());
        }
    }

    @Override
    public int getItemViewType(int position) {

        Object obj = items.get(position);
        // TODO have to update
        if (obj instanceof User) {
            User account = (User) obj;
            if (account.getType().equals(Constants.FRIEND)) return friends_type;
            else if (account.getType().equals(Constants.REQUEST)) return request_type;
            else if (account.getType().equals(Constants.SEARCH)) return search_type;
        }
        if(obj instanceof Notification) return notification_type;
        return label_type;
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
    public class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView idToSearch;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            idToSearch = (TextView) itemView.findViewById(R.id.text_search_result);
            idToSearch.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_search_result:
                    viewProfile();
                    break;
            }

        }

        public void viewProfile() {
            if (mCallback != null) {
                int pos = getAdapterPosition();
                User ut = (User) items.get(pos);
                mCallback.onViewClick(ut);
            }
        }
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView friendName;

        public FriendViewHolder(View itemView) {
            super(itemView);
            friendName = (TextView) itemView.findViewById(R.id.text_friendName);
            friendName.setOnClickListener(this);
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_friendName:
                    viewProfile();
                    break;
            }
        }

        public void viewProfile() {
            if (mCallback != null) {
                int pos = getAdapterPosition();
                User ut = (User) items.get(pos);
                mCallback.onViewClick(ut);
            }
        }
    }

    public class LabelViewHolder extends RecyclerView.ViewHolder {

        public TextView LabelName;

        public LabelViewHolder(View itemView) {
            super(itemView);
            LabelName = (TextView) itemView.findViewById(R.id.text_label);
        }

    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView confirmation;
        public Button ok;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            confirmation = (TextView) itemView.findViewById(R.id.text_confirmation);

            ok = (Button) itemView.findViewById(R.id.ok_btn);
            ok.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ok_btn:
                    okay();
                    break;
            }
        }

        public void okay() {
            if (mCallback != null) {
                int pos = getAdapterPosition();
                Notification n = (Notification) items.get(pos);
                mCallback.onOkayClick(n);
                items.remove(pos);
                notifyItemRemoved(pos);
            }
        }

    }

    public class RequestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView requ;
        public Button accept;
        public Button ignore;
        public Button view;


        public RequestsViewHolder(View itemView) {
            super(itemView);
            requ = (TextView) itemView.findViewById(R.id.text_request);

            accept = (Button) itemView.findViewById(R.id.accept_btn);
            accept.setOnClickListener(this);

            ignore = (Button) itemView.findViewById(R.id.ignore_btn);
            ignore.setOnClickListener(this);

            view = (Button) itemView.findViewById(R.id.view_btn);
            view.setOnClickListener(this);
        }


        public void accept() {
            if (mCallback != null) {
                int pos = getAdapterPosition();
                User req = (User) items.get(pos);
                mCallback.onAcceptClick(req);
                items.remove(pos);
                notifyItemRemoved(pos);
            }
        }

        public void reject() {
            if (mCallback != null) {
                int pos = getAdapterPosition();
                User req = (User) items.get(pos);
                mCallback.onIgnoreClick(req);
                items.remove(pos);
                notifyItemRemoved(pos);
            }
        }

        public void viewProfile() {
            if (mCallback != null) {
                int pos = getAdapterPosition();
                User req = (User) items.get(pos);
                mCallback.onViewClick(req);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.accept_btn:
                    accept();
                    break;
                case R.id.ignore_btn:
                    reject();
                    break;
                case R.id.view_btn:
                    viewProfile();
                    break;
            }
        }
    }

}

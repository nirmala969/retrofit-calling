package app.tutorial.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.tutorial.com.R;
import app.tutorial.com.interfaces.RecyclerClickListener;
import app.tutorial.com.model.demoapp.Model;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.UserDataViewHolder> {
    private static final String TAG = UserDataAdapter.class.getSimpleName();

    private List<Model> results;
    private Context context;
    private RecyclerClickListener rvClickListener;
    private IAddListCallback addListCallback;

    public UserDataAdapter(List<Model> results, Context context) {
        this.results = results;
        this.context = context;
    }

    public void setRvClickListener(RecyclerClickListener rvClickListener) {
        this.rvClickListener = rvClickListener;
    }

    public void setAddWatchListCallback(IAddListCallback addListCallback) {
        this.addListCallback = addListCallback;
    }

    @Override
    public UserDataAdapter.UserDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new UserDataAdapter.UserDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserDataViewHolder holder, int position) {
        holder.dob.setText("Dob: " + getFormattedDateNews(results.get(position).getDob().getDate()));
        holder.age.setText("Age: " + results.get(position).getDob().getAge().toString());
        holder.userName.setText("Name: " + results.get(position).getName().getFirst() + " " + results.get(position).getName().getLast());
        holder.gender.setText("Gender: " + results.get(position).getGender());
        holder.location.setText("Address: " + results.get(position).getLocation().getCity() + " ," +
                results.get(position).getLocation().getState() + " ," +
                results.get(position).getLocation().getCountry() + " ," +
                results.get(position).getLocation().getPostcode());
        holder.email.setText("Email: " + results.get(position).getEmail());
        holder.phone.setText("Mobile no: " + results.get(position).getPhone());

        Glide.with(context)
                .load(this.results.get(position).getPicture().getLarge())
                .centerCrop()
                .placeholder(R.drawable.ic_loading)
                .into(holder.photo);

        if (results.get(position).isAccept() && !results.get(position).isDecline()) {
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnStatus.setText("Member Accepted");
            holder.btnReject.setVisibility(View.GONE);
            holder.btnStatus.setVisibility(View.VISIBLE);
            holder.btnStatus.setBackgroundColor(context.getResources().getColor(R.color.positive));
        } else if (results.get(position).isDecline() && !results.get(position).isAccept()) {
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnStatus.setText("Member Declined");
            holder.btnReject.setVisibility(View.GONE);
            holder.btnStatus.setVisibility(View.VISIBLE);
            holder.btnStatus.setBackgroundColor(context.getResources().getColor(R.color.negative));
        } else {
            holder.btnAccept.setVisibility(View.VISIBLE);
            holder.btnReject.setVisibility(View.VISIBLE);
            holder.btnStatus.setVisibility(View.GONE);
        }

    }

    public static String getFormattedDateNews(String date) {
        String dateStr = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        try {
            Date date1 = dateFormat.parse(date);
            dateStr = simpleDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class UserDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userName;
        TextView dob;
        ImageView photo;
        TextView gender;
        TextView location;
        TextView email;
        TextView phone;
        TextView age;
        Button btnAccept;
        Button btnReject;
        Button btnStatus;

        public UserDataViewHolder(View v) {
            super(v);
            photo = (ImageView) v.findViewById(R.id.photo);
            userName = (TextView) v.findViewById(R.id.name);
            dob = (TextView) v.findViewById(R.id.dob);
            gender = (TextView) v.findViewById(R.id.gender);
            location = (TextView) v.findViewById(R.id.location);
            email = (TextView) v.findViewById(R.id.email);
            phone = (TextView) v.findViewById(R.id.phone);
            age = (TextView) v.findViewById(R.id.age);
            btnAccept = (Button) v.findViewById(R.id.btnAccept);
            btnReject = (Button) v.findViewById(R.id.btnReject);
            btnStatus = (Button) v.findViewById(R.id.btnStatus);

            btnAccept.setOnClickListener(this);
            btnReject.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnAccept:
                   /* updateScripInDB();
                    addListCallback.onAddlistSuccess(getAdapterPosition());*/
                    results.get(getAdapterPosition()).setAccept(true);
                    results.get(getAdapterPosition()).setDecline(false);
                    notifyDataSetChanged();
                    break;
                case R.id.btnReject:
                    results.get(getAdapterPosition()).setAccept(false);
                    results.get(getAdapterPosition()).setDecline(true);
                    notifyDataSetChanged();
                    break;

            }

            if (rvClickListener != null) {
                rvClickListener.onItemClick(getAdapterPosition(), view);
            }
        }
    }

    private void updateScripInDB() {
        for (int i = 0; i < results.size(); i++) {
            Model model = results.get(i);
            // If checkbox is checked, then add scrip in SavedMarketWatchModel table
//            Model model1 = new Model(i, model.getName(), model.isAccept());
            if (model.isAccept()) {
//                DatabaseHelper.addInlist(model1);
            }
        }
    }


    public interface IAddListCallback {
        void onAddlistSuccess(int position);
    }


}

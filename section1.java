package com.burs.parsa.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.burs.parsa.myapplication.module.news;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Parsa on 17/09/2017.
 */
public class section1 extends  android.support.v4.app.Fragment   {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<news> myDataset = new ArrayList();
    Context context;
    MainActivity mainActivity ;
   public onItem item;




    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAdapter = new MyAdapter(myDataset,getContext());
        View rootView = inflater.inflate(R.layout.section1, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        context = inflater.getContext();

        if(context==null)
            Log.d("is null","yes");
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(inflater.getContext()));
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(inflater.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

//        mRecyclerView.addOnItemTouchListener(
//                new RexyclerItemClickListener(inflater.getContext(), new RexyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        // TODO Handle item click
//                        Toast.makeText(getActivity(),String.valueOf( position),
//                                Toast.LENGTH_LONG).show();
//                        myDataset.remove(position);
//                            mRecyclerView.getAdapter().notifyItemRemoved(position);
//                    }
//                })
//        );
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=31889c2f9e9d4e0b9f24bcda9a63e8c7", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("error",response.toString());

                        try {
                            JSONArray  jsonArray = response.getJSONArray("articles");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("i",String.valueOf(i));
                                JSONObject current = jsonArray.getJSONObject(i);
                                news news = new news();
                                news.title=current.getString("title");
                                news.description=current.getString("description");
                                news.imageUrl = current.getString("urlToImage");
                                news.url=current.getString("url");
                                news.publishDate=current.getString("publishedAt");
                                myDataset.add(news);
                                Log.d("title",news.title);
                                mAdapter.notifyDataSetChanged();

                            }
                        }
                        catch (JSONException ex){
                            Log.d("json","error");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });


// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(inflater.getContext()).addToRequestQueue(jsObjRequest);

        mRecyclerView.setAdapter(mAdapter);

        return rootView;

    }


}
 class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {
     private ArrayList<news> mDataset;
     Context context;
     MySingleton volleySingleton;
     ImageLoader imageLoader;
     onItem onItem;



     // Provide a reference to the views for each data item
     // Complex data items may need more than one view per item, and
     // you provide access to all the views for a data item in a view holder


     // Provide a suitable constructor (depends on the kind of dataset)
     public MyAdapter(ArrayList<news> myDataset,Context context) {
         mDataset = myDataset;
        this.context = context;
         volleySingleton = MySingleton.getInstance(context);
         imageLoader = volleySingleton.getImageLoader();
     }
     public void delete(int position){
         mDataset.remove(position);
         notifyItemRemoved(position);
     }
     // Create new views (invoked by the layout manager)
     @Override
     public myViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
         // create a new view
         final View v = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.text_view, parent, false);

         // set the view's size, margins, paddings and layout parameters

         myViewHolder holder = new myViewHolder(v,context);
          onItem = new onItem() {
             @Override
             public void onItemClick(int pos) {
                 Intent intent = new Intent(context,information.class);
                 intent.putExtra("title",mDataset.get(pos).title);
                 intent.putExtra("description",mDataset.get(pos).description);
                 intent.putExtra("url",mDataset.get(pos).url);
                 intent.putExtra("imageUrl",mDataset.get(pos).imageUrl);
                 intent.putExtra("date",mDataset.get(pos).publishDate);;
                 v.getContext().startActivity(intent);
             }
         };

         return holder;
     }

     @Override
     public void onBindViewHolder(final MyAdapter.myViewHolder holder, int position) {
         news news = mDataset.get(position);
         imageLoader.get(news.imageUrl, new ImageLoader.ImageListener() {
             @Override
             public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                 holder.imageView.setImageBitmap(response.getBitmap());
                 Log.d("itsHere","yes");

             }

             @Override
             public void onErrorResponse(VolleyError error) {

             }
         });
         holder.textView.setText(mDataset.get(position).title);

     }


     // Replace the contents of a view (invoked by the layout manager)


     // Return the size of your dataset (invoked by the layout manager)
     @Override
     public int getItemCount() {
         return mDataset.size();
     }

     class myViewHolder extends RecyclerView.ViewHolder  {
         TextView textView;
         ImageView imageView;

         myViewHolder(View listView, final Context context) {
             super(listView);
             textView = (TextView) listView.findViewById(R.id.textView);
             imageView  = (ImageView)listView.findViewById(R.id.image);
             Log.d("image",imageView.toString());
             listView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int itemPosition = getLayoutPosition();
                     onItem.onItemClick(getLayoutPosition());

                 }
             });


         }

     }
 }
 class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public SimpleDividerItemDecoration(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.layout.line_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}


package com.burs.parsa.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by Parsa on 17/09/2017.
 */
public class section2 extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.section2, container, false);
        VideoView vid = (VideoView)rootView.findViewById(R.id.myVid);
        String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        Uri vidUri = Uri.parse(vidAddress);
        vid.setVideoURI(vidUri);
        MediaController vidControl = new MediaController(this.getContext());
        vidControl.setAnchorView(vid);
        vid.setMediaController(vidControl);
        return rootView;
    }
}

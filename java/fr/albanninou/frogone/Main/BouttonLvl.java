package fr.albanninou.frogone.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import fr.albanninou.frogone.Lvl.LvlActivity;
import fr.albanninou.frogone.Tuto.TutoActivity;

public class BouttonLvl extends Button {
    public BouttonLvl(Context context, int lvlatteint, final int lvl, final Activity activity) {
        super(context);
        if (lvl == 0) {
            this.setText("Tutoriel");
            this.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(activity, TutoActivity.class);
                    activity.startActivity(intent);
                }
            });
        } else {
            if (lvl <= lvlatteint) {
                this.setText("Lvl " + lvl);
                this.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, LvlActivity.class);
                        intent.putExtra("lvl", lvl);
                        activity.startActivity(intent);
                    }
                });
            } else {
                this.setText("Verrouillé");
            }
        }
    }
}

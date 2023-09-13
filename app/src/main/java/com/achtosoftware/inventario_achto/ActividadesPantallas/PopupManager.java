package com.achtosoftware.inventario_achto.ActividadesPantallas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.achtosoftware.inventario_achto.*;
import com.achtosoftware.inventario_achto.RECIBO.pantalla_recibo;

public class PopupManager {



//////////////////////////////// popups para recibo ////////////////////////////////////////////////
    public static void mostrarPopupwarning(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.popup_stylewarning, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        Button okButton = popupView.findViewById(R.id.btnDialogFirstButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////// popups para embarque //////////////////////////////////////////
    public static void mostrarPopupwarningembarque(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.popup_stylewarning, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        TextView mensaje = popupView.findViewById(R.id.tvDialogDesc);
        Button okButton = popupView.findViewById(R.id.btnDialogFirstButton);

        mensaje.setText("La cantidad a embarcar excede a la cantidad solicitada");

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public static void mostrarPopupwarningembarquefisico(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.popup_stylewarning, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        TextView mensaje = popupView.findViewById(R.id.tvDialogDesc);
        Button okButton = popupView.findViewById(R.id.btnDialogFirstButton);

        mensaje.setText("No hay suficiente cantidad en inventario para poder embarcar");
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }



}
package com.example.practicacanvas;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

public class Pelota extends View {

    private Drawable balon,campo;
    private Context contexto;
    private int borde = 12;
    private int ancho, alto,contador;
    public int izquierda, derecha;
    private int tamanio = 100;
    private float ejeX = 0, ejeY = 0, ejeZ = 0;
    public String X, Y, Z;


    public Pelota(Context context) {
        super(context);
        this.contexto=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint pincelTexto = new Paint();
        Paint pincelRecta = new Paint();

        izquierda = ancho / 2 - 130;
        derecha = ancho/ 2 + 130;

        campo=contexto.getResources().getDrawable(R.drawable.campo);
        campo.setBounds(0,0,this.ancho,this.alto);
        campo.draw(canvas);

        pincelTexto.setColor(Color.BLACK);
        pincelTexto.setTextSize(35);
        pincelTexto.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("Coordenada X: " + X, canvas.getWidth() / 2, 40, pincelTexto);
        canvas.drawText("Coordenada Y: " + Y, canvas.getWidth() / 2, 80, pincelTexto);
        canvas.drawText("Coordenada Z: " + Z, canvas.getWidth() / 2, 120, pincelTexto);
        pincelTexto.setTextSize(60);
        pincelTexto.setTextAlign(Paint.Align.CENTER);
        pincelTexto.setColor(contexto.getResources().getColor(R.color.texto));
        canvas.drawText("Goles: " + this.contador, canvas.getWidth() / 2, 220, pincelTexto);

        pincelRecta.setColor(Color.RED);
        pincelRecta.setStrokeWidth(20);
        pincelRecta.setStyle(Paint.Style.STROKE);

        canvas.drawLine(izquierda,canvas.getHeight(),derecha,canvas.getHeight(),pincelRecta);
        balon=contexto.getResources().getDrawable(R.drawable.pelota);
        balon.setBounds((int)(this.ejeX-tamanio*1.05),(int)(this.ejeY-tamanio*1.05),(int)(this.ejeX+tamanio*1.05),(int)(this.ejeY+tamanio*1.05));
        balon.draw(canvas);
    }

    public int getAncho() {
        return ancho;
    }
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }
    public int getAlto() {
        return alto;
    }
    public void setAlto(int alto) {
        this.alto = alto;
    }
    public float getEjeX() {
        return ejeX;
    }
    public void setEjeX(float ejeX) {
        this.ejeX = ejeX;
    }
    public void setEjeY(float ejeY) {
        this.ejeY = ejeY;
    }
    public float getEjeZ() {
        return ejeZ;
    }
    public void setEjeZ(float ejeZ) {
        this.ejeZ = ejeZ;
    }
    public int getTamanio() {
        return tamanio;
    }
    public int getBorde() {
        return borde;
    }
    public void setContador(int contador){
        this.contador=contador;
    }
}




package com.sodenet.hipotecas;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.graphics.Color;

public class EstacionCursorAdapter extends CursorAdapter
{
	
	private EstacionesDbAdapter dbAdapter = null ;
	
	public EstacionCursorAdapter(Context context, Cursor c)
	{
		super(context, c);
		dbAdapter = new EstacionesDbAdapter(context);
		dbAdapter.abrir();
	}

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView tv = (TextView) view ;

        tv.setText(cursor.getString(cursor.getColumnIndex(EstacionesDbAdapter.C_COLUMNA_NOMBRE)));

        /*
        if (cursor.getString(cursor.getColumnIndex(EstacionesDbAdapter.)).equals("S"))
        {
            tv.setTextColor(Color.GRAY);
        }
        else
        {
            tv.setTextColor(Color.BLACK);
        }
        */
    }

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		final LayoutInflater inflater = LayoutInflater.from(context);
		final View view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
		
		return view;
	}
}

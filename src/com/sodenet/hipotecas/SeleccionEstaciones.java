package com.sodenet.hipotecas;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

public class SeleccionEstaciones extends ListActivity {
	
    public static final String C_MODO  = "modo" ;
    public static final int C_VISUALIZAR = 551 ;
    public static final int C_CREAR = 552 ;
    public static final int C_EDITAR = 553 ;
    public static final int C_ELIMINAR = 554 ;
    public static final int C_CONFIGURAR = 555 ;
		
	private EstacionesDbAdapter dbAdapter;
    private Cursor cursor; 
    private EstacionCursorAdapter estacionAdapter ;
    private ListView lista;
    private Spinner cbEstaciones;

    private String filtro ;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hipoteca);

        getPreferencias();

        lista = (ListView) findViewById(android.R.id.list);
        cbEstaciones = (Spinner) findViewById(R.id.cbEstaciones);

        dbAdapter = new EstacionesDbAdapter(this);
        dbAdapter.abrir();

        //
        // Creamos el adaptador de Situacion
        //
        dbAdapter = new EstacionesDbAdapter(this) ;
        dbAdapter.abrir();

        cursor = dbAdapter.getCursor(filtro);

        SimpleCursorAdapter adapterEstaciones = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,cursor,new String[] {EstacionesDbAdapter.C_COLUMNA_NOMBRE}, new int[] {android.R.id.text1});

        adapterEstaciones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cbEstaciones.setAdapter(adapterEstaciones);



        consultar();

        registerForContextMenu(this.getListView());
    }

    private void consultar()
    {
        cursor = dbAdapter.getCursor(filtro);
        startManagingCursor(cursor);
        estacionAdapter = new EstacionCursorAdapter(this, cursor);
        lista.setAdapter(estacionAdapter);
    }
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hipoteca, menu);
		return true;
	}
	
	private void visualizar(long id)
	{
		// Llamamos a la Actividad HipotecaFormulario indicando el modo visualización y el identificador del registro 
		/*Intent i = new Intent(SeleccionEstaciones.this, HipotecaFormulario.class);
		i.putExtra(C_MODO, C_VISUALIZAR);
		i.putExtra(EstacionesDbAdapter.C_COLUMNA_ID, id);

		startActivityForResult(i, C_VISUALIZAR);
		*/
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		
		visualizar(id);
	}

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {
        Intent i;

        switch (item.getItemId())
        {
            /*
            case R.id.menu_crear:
                i = new Intent(SeleccionEstaciones.this, HipotecaFormulario.class);
                i.putExtra(C_MODO, C_CREAR);
                startActivityForResult(i, C_CREAR);
                return true;*/

            case R.id.menu_preferencias:
                i = new Intent(SeleccionEstaciones.this, Configuracion.class);
                startActivityForResult(i, C_CONFIGURAR);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //
        // Nos aseguramos que es la petición que hemos realizado
        //
        switch(requestCode)
        {
            case C_CREAR:
                if (resultCode == RESULT_OK)
                    consultar();

            case C_VISUALIZAR:
                if (resultCode == RESULT_OK)
                    consultar();

            case C_CONFIGURAR:
                // en la PreferenceActivity no hemos definido ningún resultado por lo que recargamos
                // siempre las preferencias
                getPreferencias();
                consultar();

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
	
	private void borrar(final long id)
	{
		/*
		 * Borramos el registro y refrescamos la lista
		 */
		AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);
		
		dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
		dialogEliminar.setTitle(getResources().getString(R.string.hipoteca_eliminar_titulo));
		dialogEliminar.setMessage(getResources().getString(R.string.hipoteca_eliminar_mensaje));
		dialogEliminar.setCancelable(false);
		
		dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int boton) {
				dbAdapter.delete(id);
				Toast.makeText(SeleccionEstaciones.this, R.string.hipoteca_eliminar_confirmacion, Toast.LENGTH_SHORT).show();
				consultar();				
			}
		});
		
		dialogEliminar.setNegativeButton(android.R.string.no, null);
		
		dialogEliminar.show();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		menu.setHeaderTitle(cursor.getString(cursor.getColumnIndex(EstacionesDbAdapter.C_COLUMNA_NOMBRE)));
		menu.add(Menu.NONE, C_VISUALIZAR, Menu.NONE, R.string.menu_visualizar);
		menu.add(Menu.NONE, C_EDITAR, Menu.NONE, R.string.menu_editar);
		menu.add(Menu.NONE, C_ELIMINAR, Menu.NONE, R.string.menu_eliminar);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Intent i;
		
		switch(item.getItemId())
		{
	    	case C_ELIMINAR:
	    		borrar(info.id);
	    		return true;
	    	
	    	case C_VISUALIZAR:
	    		visualizar(info.id);
				return true;
				
	    	/*case C_EDITAR:
	    		i = new Intent(SeleccionEstaciones.this, HipotecaFormulario.class);
	    		i.putExtra(C_MODO, C_EDITAR);
	    		i.putExtra(EstacionesDbAdapter.C_COLUMNA_ID, info.id);
				
	    		startActivityForResult(i, C_EDITAR);
				return true;
	        */
	    }
	    return super.onContextItemSelected(item);
	}

    private void getPreferencias()
    {
        //
        // Recuperamos las preferencias
        //
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);

       /* if (preferencias.getBoolean("ocultar_registros_pasivos", false))
        {
            // si se ocultan registros pasivos filtramos solamente los que tengan el valor 'N'
            this.filtro = EstacionesDbAdapter.C_COLUMNA_PASIVO + " = 'N' " ;
        }
        else
        {
            // si no se ocultan registros pasivos no filtramos
            this.filtro = null ;
        }
        */
    }
}
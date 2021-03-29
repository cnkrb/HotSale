package com.cenkkaraboa.kuponuygulamas.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cenkkaraboa.kuponuygulamas.R;
import com.cenkkaraboa.kuponuygulamas.activitites.MainActivity;
import com.cenkkaraboa.kuponuygulamas.adapters.LikeAdapter;
import com.cenkkaraboa.kuponuygulamas.adapters.SalesAdapter;
import com.cenkkaraboa.kuponuygulamas.models.DefaultResponse;
import com.cenkkaraboa.kuponuygulamas.models.Like;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cenkkaraboa.kuponuygulamas.Utils.Utils.interfaces;


public class SalesFragment extends Fragment   implements  SalesAdapter.OnItemClickListener{



    View view;
    String ID;
    RecyclerView recyclerView;
   public static SalesAdapter salesAdapter;
    final int PICK_IMAGE = 100;
    String id;
    Uri picUri;
    Bitmap mBitmap = null;
    ProgressDialog progressDialog;
    MultipartBody.Part pBitmap = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_sales, container, false);

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        ID=preferences.getString("ID","null");


        recyclerView=view.findViewById(R.id.recyclerView);

        load();
        return view;
    }

    public void load() {
        Callback<List<Like>> listCallBack = new Callback<List<Like>>() {
            @Override
            public void onResponse(Call<List<Like>> call, Response<List<Like>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        salesAdapter = new SalesAdapter(response.body(), getContext());
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        recyclerView.setAdapter(salesAdapter);
                        salesAdapter.setOnItemClickListener(SalesFragment.this);
                        salesAdapter.notifyDataSetChanged();
                    } else {
                        if (salesAdapter != null) {
                            salesAdapter = new SalesAdapter(response.body(), getContext());
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                            recyclerView.setAdapter(salesAdapter);
                            salesAdapter.setOnItemClickListener(SalesFragment.this);
                            salesAdapter.notifyDataSetChanged();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Like>> call, Throwable t) {
            }
        };
        interfaces.salesList("sales", ID, "").enqueue(listCallBack);
    }

    @Override
    public void onItemClick(String position, boolean value) {
        id=position;
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, PICK_IMAGE);
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getContext(),"İzin vermeden galeriye erişemezsiniz",Toast.LENGTH_SHORT).show();

                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    private void multipartImageUpload() {
        pBitmap = dada(mBitmap, 0);


        Callback<DefaultResponse> listCallBack = new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if(response.code()==200){
                        if(response.body().getResult()){
                            Toast.makeText(getContext(), "Yüklendi", Toast.LENGTH_SHORT).show();
                            load();
                        }else {

                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        Toast.makeText(getContext(), "Yüklenemedi", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                System.out.println(t.getCause());
                System.out.println(t.getMessage() + "cececece");
            }
        };
        interfaces.salesImage("sales", ID,id, pBitmap).enqueue(listCallBack);


    }

    public MultipartBody.Part dada(Bitmap bitmap, int i) {
        MultipartBody.Part body = null;

        if (bitmap != null) {
            try {
                File filesDir = getActivity().getFilesDir();
                File file = null;
                if (i == 0) {
                    file = new File(filesDir, "image" + ".png");
                }
                OutputStream os;
                try {
                    os = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                if (i == 0) {
                    body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
                    System.out.println(file.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("pic_uri", picUri);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE && data!=null) {
            try {
                if (data.getData() != null) {
                    System.out.println("burada");
                    Uri uri = data.getData();
                    String path = getRealPathFromURI(getContext(), uri);
                    mBitmap = decodeFile(path);


                    progressDialog = new ProgressDialog(getContext(),
                            R.style.Progress);
                    progressDialog.setCancelable(false);
                    progressDialog = ProgressDialog.show(getContext(), null, "Resim yükleniyor...", true);
                    multipartImageUpload();



                } else {
                    Toast.makeText(getContext(), "Resim niye seçmiyon", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {

                Toast.makeText(getContext(), "Bazı şeyler sıkıntılı", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Bitmap decodeFile(String filePath) {

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        final int REQUIRED_SIZE = 1024;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap image = BitmapFactory.decodeFile(filePath, o2);

        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            int rotate = 0;
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            if (rotate != 0) {
                int w = image.getWidth();
                int h = image.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap & convert to ARGB_8888, required by tess
                image = Bitmap.createBitmap(image, 0, 0, w, h, mtx, false);

            }
        } catch (IOException e) {
            return null;
        }
        return image.copy(Bitmap.Config.ARGB_8888, true);
    }


}
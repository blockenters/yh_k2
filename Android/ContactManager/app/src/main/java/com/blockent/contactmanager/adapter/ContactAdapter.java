package com.blockent.contactmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blockent.contactmanager.R;
import com.blockent.contactmanager.model.Contact;

import java.util.List;

// row 화면에 맵핑할 어댑터 클래스 만드는 순서

// 1. RycylerView.Adapter 를 상속받는다.
//  unimplemented method 모두 선택해서 넣는다.

// 5. RecyclerView.Adapter 의 데이터타입을 알려줘야 한다.
//    우리가 만든 ViewHolder 의 타입으로 설정 => 그러면 빨간색 뜬다.
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    // 4. 어댑터 클래스의 멤버변수와 생성자를 만들어 준다.
    Context context;
    List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    // 6. 아래 함수들 구현.
    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);
        return new ContactAdapter.ViewHolder(view) ;
    }

    // 메모리에 있는 데이터(리스트) 를 화면에 표시하는 함수.
    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        holder.txtName.setText(contact.name);
        holder.txtPhone.setText(contact.phone);

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    // 2. ViewHolder 클래스를 만든다.
    //    이 클래스는 row.xml 화면에 있는 뷰를 연결 시키는 클래스
    //    빨간색 눌러서 생성자 만든다.
    //    화면과 연결할 자바 변수를 만든다.
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtPhone;
        ImageView imgDelete;

        // 3. 생성자 안에다가, 연결시키는 코드를 작성한다.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo : 엑스이미지 누르면, 해당 주소록 삭제하도록 개발!
                }
            });

        }
    }

}

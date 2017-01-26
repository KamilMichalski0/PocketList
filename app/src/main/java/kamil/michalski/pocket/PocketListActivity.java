package kamil.michalski.pocket;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.ACTION_DIAL;

public class PocketListActivity extends AppCompatActivity implements LinksAdapter.ActionListener{
    @BindView(R.id.activity_pocket_list)
    RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocket_list);
        ButterKnife.bind(this);
        // wyswietlanie elementow w pionie
        mList.setLayoutManager(new LinearLayoutManager(this));

        ILinkDatabase database = new SqlliteLinkDatabase(this);
        LinksAdapter adapter = new LinksAdapter(database.getLinks(), this);

        mList.setAdapter(adapter);
        //podpinamy adapter do listy
    }

    @Override
    public void OnActionClick(View anchor,Link link) {
        if (link.getType()==Link.TYPE_LINK){
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(link.getReference()));
            startActivity(intent);
        }
        else if (link.getType()==Link.TYPE_PHONE){
            PopupMenu menu=new PopupMenu(this,anchor);
            menu.inflate(R.menu.action_menu);
            menu.show();
          //  Intent intent = new Intent(ACTION_DIAL, Uri.parse("tel"+link.getReference()));
          //  startActivity(intent);
        }

    }
}

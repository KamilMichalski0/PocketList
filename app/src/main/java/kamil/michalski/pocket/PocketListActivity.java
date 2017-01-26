package kamil.michalski.pocket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PocketListActivity extends AppCompatActivity implements LinksAdapter.ActionListener, PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.activity_pocket_list)
    RecyclerView mList;
    private Link mLink;
    @BindView(R.id.fab)
    FloatingActionButton  mFab;


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
    @OnClick(R.id.fab)
    void OnfabClick(){
        Intent intent = new Intent(this,CreateElementActivity.class);
        startActivity(intent);
    }

    @Override
    public void OnActionClick(View anchor, Link link) {
        mLink = link;
        if (link.getType() == Link.TYPE_LINK) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getReference()));
            startActivity(intent);
        } else if (link.getType() == Link.TYPE_PHONE) {
            PopupMenu menu = new PopupMenu(this, anchor);
            menu.setGravity(Gravity.RIGHT);
            menu.inflate(R.menu.action_menu);
            menu.setOnMenuItemClickListener(this);
            menu.show();
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_call) {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mLink.getReference()));
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_sms) {
            Intent intent = new Intent(Intent.ACTION_SENDTO,
                    Uri.parse("sms:" + mLink.getReference()));
            startActivity(intent);
        }
        return true;
    }
}

package kamil.michalski.pocket;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ppg38 on 08.01.2017.
 */

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.LinkViewHolder> {
    private List<Link> mLinks;
    private ActionListener mActionListener;

    public LinksAdapter(List<Link> mLinks, ActionListener mActionListener) {
        this.mLinks = mLinks;
        this.mActionListener = mActionListener;
    }

    @Override
    public LinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.row_pocket_item, parent, false);
        return new LinkViewHolder(rowView);

    }

    @Override
    public void onBindViewHolder(LinkViewHolder holder, int position) {

        Link item = mLinks.get(position);
        holder.mCurrentLink = item;

        holder.mLinkTitle.setText(item.getName());
        holder.mLinkReference.setText(item.getReference());
        if (item.getType() == Link.TYPE_PHONE) {
            holder.mLinkSymbol.setImageResource(android.R.drawable.ic_menu_call);
        } else if (item.getType() == Link.TYPE_LINK) {
            holder.mLinkSymbol.setImageResource(android.R.drawable.ic_menu_share);
        }
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public class LinkViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.link_title)
        TextView mLinkTitle;
        @BindView(R.id.link_reference)
        TextView mLinkReference;
        @BindView(R.id.link_symbol)
        ImageView mLinkSymbol;

        Link mCurrentLink;

        public LinkViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.link_symbol)
        void onSymbolClick(View clicked) {
            if (mActionListener != null) {
                mActionListener.OnActionClick(clicked,mCurrentLink);
            }
        }
    }

    public interface ActionListener {
        void OnActionClick(View anchor, Link link);
    }
}

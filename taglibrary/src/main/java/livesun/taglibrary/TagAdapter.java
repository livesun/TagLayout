package livesun.taglibrary;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 29028 on 2017/7/25.
 */

public abstract class TagAdapter {

    public abstract int getItemCount();

    public abstract View getView(int position, ViewGroup parent);
}

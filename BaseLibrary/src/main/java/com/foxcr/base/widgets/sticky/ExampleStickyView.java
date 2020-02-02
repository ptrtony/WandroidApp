package com.foxcr.base.widgets.sticky;

import android.view.View;

public class ExampleStickyView implements StickyView {
    @Override
    public boolean isStickyView(View view) {
        return (boolean)view.getTag();
    }

    @Override
    public int getStickViewType() {
        return 1;
    }
}

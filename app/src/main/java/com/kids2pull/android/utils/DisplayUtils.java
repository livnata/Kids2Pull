package com.kids2pull.android.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kids2pull.android.loggers.Logger;

public class DisplayUtils {

    public static void showInfo(Context context, String info) {
        toast(context, info);
    }

    public static void showInfo(Context context, int resId) {
        toast(context, context.getString(resId));
    }

    public static void toast(Context context, String message) {
        if (context != null) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            //toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, -20);
            toast.show();
        } else {
            Logger.e("DisplayUtils", "Can't display toast since context is null");

        }
    }

//    public static GeneralFragmentDialog fragmentDialog(final FragmentManager fragmentManager, Handler handler, final String title, final String body,
//                                                       final String buttonYes, final String buttonNo) {
//
//        return fragmentDialog(fragmentManager, handler, title, body, buttonYes, buttonNo, true, false);
//    }

//    public static GeneralFragmentDialog fragmentDialogNoCancelable(final FragmentManager fragmentManager, Handler handler, final String title, final String body,
//                                                                   final String buttonYes) {
//        return fragmentDialog(fragmentManager, handler, title, body, buttonYes, null, false, false);
//    }
//
//    public static GeneralFragmentDialog fragmentDialogNoCancelable(final FragmentManager fragmentManager, Handler handler, final String title, final String body,
//                                                                   final String buttonYes, final String buttonNo) {
//
//        return fragmentDialog(fragmentManager, handler, title, body, buttonYes, buttonNo, false, false);
//    }
//
//    public static GeneralFragmentDialog fragmentDialog(final FragmentManager fragmentManager, Handler handler, final String title, final String body,
//                                                       final String buttonYes, final String buttonNo) {
//
//        return fragmentDialog(fragmentManager, handler, title, body, buttonYes, buttonNo, true, false);
//    }
//
//    public static GeneralFragmentDialog fragmentDialogHtml(final FragmentManager fragmentManager, Handler handler, final String title, final String body,
//                                                           final String buttonYes, final String buttonNo) {
//
//        return fragmentDialog(fragmentManager, handler, title, body, buttonYes, buttonNo, true, true);
//    }
//
//
//    /**
//     * @param fragmentManager the fragment manager
//     * @param handler         new handler
//     * @param title           the title of the pop up
//     * @param body            the body of the pop up
//     * @param buttonYes       the text for button or null if not to show
//     * @param buttonNo        the text for button or null if not to show
//     * @return
//     */
//    public static GeneralFragmentDialog fragmentDialog(final FragmentManager fragmentManager, Handler handler, final String title, final String body,
//                                                       final String buttonYes, final String buttonNo, final boolean isCancelable, final boolean isHtmlTags) {
//
//        final GeneralFragmentDialog fragmentDialog = GeneralFragmentDialog.newInstance();
//
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                Bundle data = new Bundle();
//                data.putString(GeneralFragmentDialog.PARAM_TITLE, title);
//                data.putString(GeneralFragmentDialog.PARAM_TEXT, body);
//                data.putString(GeneralFragmentDialog.PARAM_POSITIVE_BUTTON, buttonYes);
//                data.putString(GeneralFragmentDialog.PARAM_NEGATIVE_BUTTON, buttonNo);
//                data.putBoolean(GeneralFragmentDialog.PARAM_USE_HTML, isHtmlTags);
//                fragmentDialog.setArguments(data);
//                fragmentDialog.setCancelable(isCancelable);
//                fragmentDialog.show(fragmentManager, GeneralFragmentDialog.FRAGMENT_TAG);
//            }
//        });
//
//        return fragmentDialog;
//    }
//
//
//    public static DidYouMeanFragmentDialog fragmentAddressDidYouMeanDialog(final FragmentManager fragmentManager, final Handler handler, final String title, final ArrayList<Geocode> geocodes,
//                                                                           final String buttonYes, final String buttonNo) {
//        final DidYouMeanFragmentDialog fragmentDialog = DidYouMeanFragmentDialog.newInstance();
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//
//                Bundle data = new Bundle();
//                data.putString(DidYouMeanFragmentDialog.PARAM_TITLE, title);
//                data.putSerializable(DidYouMeanFragmentDialog.PARAM_LIST, geocodes);
//                data.putString(DidYouMeanFragmentDialog.PARAM_POSITIVE_BUTTON, buttonYes);
//                data.putString(DidYouMeanFragmentDialog.PARAM_NEGATIVE_BUTTON, buttonNo);
//                fragmentDialog.setArguments(data);
//                fragmentDialog.show(fragmentManager, DidYouMeanFragmentDialog.FRAGMENT_TAG);
//            }
//        });
//
//        return fragmentDialog;
//    }
//
//
//    public static FixedPriceFragmentDialog fragmentFixedPriceDialog(final FragmentManager fragmentManager,
//                                                                    final Handler handler, final FixPriceEntity fixPriceEntity,
//                                                                    final String buttonYes, final String buttonNo) {
//
//        final FixedPriceFragmentDialog fragmentDialog = FixedPriceFragmentDialog.newInstance();
//
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//
//                Bundle data = new Bundle();
//                data.putSerializable(FixedPriceFragmentDialog.PARAM_FIX_PRICE_OBJECT, fixPriceEntity);
//                data.putString(FixedPriceFragmentDialog.PARAM_POSITIVE_BUTTON, buttonYes);
//                data.putString(FixedPriceFragmentDialog.PARAM_NEGATIVE_BUTTON, buttonNo);
//
//                fragmentDialog.setArguments(data);
//                fragmentDialog.show(fragmentManager, FixedPriceFragmentDialog.FRAGMENT_TAG);
//            }
//        });
//
//        return fragmentDialog;
//    }
//
//    public static ConnectionErrorFragmentDialog connectionErrorFragmentDialog(final FragmentManager fragmentManager, Handler handler, final String body,
//                                                                              final String buttonYes) {
//
//        final ConnectionErrorFragmentDialog fragmentDialog = new ConnectionErrorFragmentDialog();
//
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                Bundle data = new Bundle();
//                data.putString(GeneralFragmentDialog.PARAM_TEXT, body);
//                data.putString(GeneralFragmentDialog.PARAM_POSITIVE_BUTTON, buttonYes);
//                fragmentDialog.setArguments(data);
//                fragmentDialog.show(fragmentManager, GeneralFragmentDialog.FRAGMENT_TAG);
//            }
//        });
//
//        return fragmentDialog;
//    }
//
//    public static CancellationFeePolicyFragmentDialog cancellationFeePolicyFragmentDialog(final FragmentManager fragmentManager, Handler handler, final String title, final String body, final String comment,
//                                                                                          final String buttonYes, final String buttonNo) {
//
//        final CancellationFeePolicyFragmentDialog fragmentDialog = new CancellationFeePolicyFragmentDialog();
//
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                Bundle data = new Bundle();
//                data.putString(GeneralFragmentDialog.PARAM_TITLE, title);
//                data.putString(GeneralFragmentDialog.PARAM_TEXT, body);
//                data.putString(GeneralFragmentDialog.PARAM_COMMENT, comment);
//                data.putString(GeneralFragmentDialog.PARAM_POSITIVE_BUTTON, buttonYes);
//                data.putString(GeneralFragmentDialog.PARAM_NEGATIVE_BUTTON, buttonNo);
//                data.putBoolean(GeneralFragmentDialog.PARAM_USE_HTML, true);
//                fragmentDialog.setCancelable(false);
//                fragmentDialog.setArguments(data);
//                fragmentDialog.show(fragmentManager, GeneralFragmentDialog.FRAGMENT_TAG);
//            }
//        });
//
//        return fragmentDialog;
//    }
//
//    public static DivisionPromoDialog divisionPromoFragmentDialog(final FragmentManager fragmentManager, Handler handler, PromoInfo info, final String source, final boolean isaActive) {
//
//        final DivisionPromoDialog dialog = DivisionPromoDialog.newInstance(info, source, isaActive);
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                dialog.show(fragmentManager, DivisionPromoDialog.FRAGMENT_TAG);
//            }
//        });
//
//        return dialog;
//    }
//
//    public static GeneralWithImageFragmentDialog generalWithImageFragmentDialog(final FragmentManager fragmentManager,
//                                                                                Handler handler, final String title,
//                                                                                final String body, final String comment, final int imageId,
//                                                                                final String buttonYes, final String buttonNo) {
//
//        final GeneralWithImageFragmentDialog generalWithImageFragmentDialog = new GeneralWithImageFragmentDialog();
//
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                Bundle data = new Bundle();
//                data.putString(GeneralWithImageFragmentDialog.PARAM_TITLE, title);
//                data.putString(GeneralWithImageFragmentDialog.PARAM_TEXT, body);
//                data.putString(GeneralWithImageFragmentDialog.PARAM_COMMENT, comment);
//                data.putInt(GeneralWithImageFragmentDialog.PARAM_IMAGE, imageId);
//                data.putString(GeneralWithImageFragmentDialog.PARAM_POSITIVE_BUTTON, buttonYes);
//                data.putString(GeneralWithImageFragmentDialog.PARAM_NEGATIVE_BUTTON, buttonNo);
//                generalWithImageFragmentDialog.setCancelable(false);
//                generalWithImageFragmentDialog.setArguments(data);
//                generalWithImageFragmentDialog.show(fragmentManager, GeneralWithImageFragmentDialog.FRAGMENT_TAG);
//            }
//        });
//
//        return generalWithImageFragmentDialog;
//    }
//
//    public static GeneralWithImageFragmentDialog generalWithImageFragmentDialog(final FragmentManager fragmentManager,
//                                                                                Handler handler, final String title, final String partTitle,
//                                                                                final String body, final String comment, final int imageId,
//                                                                                final String buttonYes, final String buttonNo) {
//
//        final GeneralWithImageFragmentDialog generalWithImageFragmentDialog = new GeneralWithImageFragmentDialog();
//
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                Bundle data = new Bundle();
//                data.putString(GeneralWithImageFragmentDialog.PARAM_TITLE, title);
//                data.putString(GeneralWithImageFragmentDialog.PARAM_PART_TITLE, partTitle);
//                data.putString(GeneralWithImageFragmentDialog.PARAM_TEXT, body);
//                data.putString(GeneralWithImageFragmentDialog.PARAM_COMMENT, comment);
//                data.putInt(GeneralWithImageFragmentDialog.PARAM_IMAGE, imageId);
//                data.putString(GeneralWithImageFragmentDialog.PARAM_POSITIVE_BUTTON, buttonYes);
//                data.putString(GeneralWithImageFragmentDialog.PARAM_NEGATIVE_BUTTON, buttonNo);
//                generalWithImageFragmentDialog.setCancelable(false);
//                generalWithImageFragmentDialog.setArguments(data);
//                generalWithImageFragmentDialog.show(fragmentManager, GeneralWithImageFragmentDialog.FRAGMENT_TAG);
//            }
//        });
//
//        return generalWithImageFragmentDialog;
//    }
//
//    public static CardExpiryFragmentDialog fragmentCardExpiryDialog(final FragmentManager fragmentManager,
//                                                                    final Handler handler, final CreditCard creditCard) {
//
//        final CardExpiryFragmentDialog fragmentDialog = CardExpiryFragmentDialog.newInstance();
//
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                Bundle data = new Bundle();
//                data.putSerializable(CardExpiryFragmentDialog.PARAM_EXPIRY_CREDIT_CARD, creditCard);
//                fragmentDialog.setArguments(data);
//                fragmentDialog.show(fragmentManager, CardExpiryFragmentDialog.FRAGMENT_TAG);
//            }
//        });
//
//        return fragmentDialog;
//    }
//
//    /**
//     * this function return divider view, used for radio group where sdk < honeycomb
//     *
//     * @param height
//     * @return the divider
//     */
//    public static View makeVerticalDivider(Context ctx, int height) {
//        ImageView view = new ImageView(ctx);
//        view.setBackgroundResource(R.drawable.dark_divider);
//        int ht_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, ctx.getResources().getDisplayMetrics());
//        int wt_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, ctx.getResources().getDisplayMetrics());
//        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(wt_px, ht_px);
//        params.gravity = Gravity.CENTER_VERTICAL;
//        view.setLayoutParams(params);
//        return view;
//    }

    public static View makeVerticalDivider(Context ctx, int color, int marginTop, int marginBottom) {
        View view = new View(ctx);
        view.setBackgroundResource(color);
        int wt_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, ctx.getResources().getDisplayMetrics());
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(wt_px, LayoutParams.MATCH_PARENT);
        params.setMargins(0, marginTop, 0, marginBottom);
        params.gravity = Gravity.CENTER_VERTICAL;
        view.setLayoutParams(params);
        return view;
    }

    /**
     * simple expand animation with 200 millisecond duration
     * @param v the view
     */
    public static void expand(final View v) {
        expand(v, null);
    }

    /**
     * simple expand animation with 200 millisecond duration
     * @param v the view
     * @param animationListener listener for the animation (null if not set)
     */
    public static void expand(final View v, Animation.AnimationListener animationListener) {
        expand(v, 200, animationListener);
    }

    /**
     * simple expand animation
     * @param v the view
     * @param duration time for animation
     * @param animationListener listener for the animation (null if not set)
     */
    public static void expand(final View v, long duration, Animation.AnimationListener animationListener) {
        expand(v,duration,-1,animationListener);
    }

    /**
     * simple expand animation
     * @param v the view
     * @param duration time for animation
     * @param height the height of the view in pixel (-1 if not const)
     * @param animationListener listener for the animation (null if not set)
     */
    public static void expand(final View v, long duration, int height, Animation.AnimationListener animationListener) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final int targetHeight = height > 0 ? height : v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        //a.setDurationInMs((int)(targetetHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(duration);
        if(animationListener != null){
            a.setAnimationListener(animationListener);
        }
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        collapse(v, null);
    }

    public static void collapse(final View v, Animation.AnimationListener animationListener) {
        collapse(v,200,animationListener);
    }

    public static void collapse(final View v, long duration, Animation.AnimationListener animationListener) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        //a.setDurationInMs((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        if(animationListener != null){
            a.setAnimationListener(animationListener);
        }
        a.setDuration(duration);
        v.startAnimation(a);
    }

//    public static int getClassVipUpgradeImageByType(String imageName) {
//        int resId = 0;
//
//        if (Enums.ClassDivision.IL_PREMIUM.equalsIgnoreCase(imageName)) {
//            resId = R.drawable.upgradetoclass2_il;
//        } else if (Enums.ClassDivision.COMFORT.equalsIgnoreCase(imageName)) {
//            resId = R.drawable.upgradetoclass2_ru;
//        } else if (Enums.ClassDivision.VIP.equalsIgnoreCase(imageName)) {
//            resId = R.drawable.upgradetoclass3_ru;
//        }else {
//            resId = R.drawable.upgradetoclass_generic;
//        }
//
//        return resId;
//    }
//
//    public static boolean isCardInObList(CreditCard card) {
//        if(Settings.getInstance().hasOutstandingBalance()){
//            return Settings.getInstance().getOutstandingBalance().getRelevantCards().contains(card.getCardId());
//        }
//        return false;
//    }

//    public static boolean isAutomationBuild(){
//        return "automation".equalsIgnoreCase(BuildConfig.BUILD_TYPE);
//    }
//
//    public static int getFavoriteIconIdByType(FavoriteGeocode geocode){
//        switch(geocode.getFavoriteType()){
//            case Enums.Favorite.HOME:
//                return R.drawable.ic_search_home;
//            case Enums.Favorite.WORK:
//                return R.drawable.ic_search_work;
//            default:
//                return R.drawable.ic_search_fav;
//        }
//    }

//    public static void setScreenTag(String tag) {
//        Appsee.startScreen(tag);
//    }


//    public static boolean showBusinessPromo() {
//        return AppProfile.getInstance().getBusinessPromoRelevant() &&
//                Settings.getInstance().isShowBusinessPromo() &&
//                !Settings.getInstance().getUser().hasCompany();
//    }

    public static ImageView buildBusinessPromoDotImage(Context context, boolean isForSpinner) {
        ShapeDrawable circle= new ShapeDrawable( new OvalShape());
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                context.getResources().getDisplayMetrics());
        circle.setIntrinsicHeight(size);
        circle.setIntrinsicWidth(size);
//        circle.getPaint().setColor(context.getResources().getColor(R.color.guid_c9));

        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams layoutParams;
        if (isForSpinner) {
           layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                    context.getResources().getDisplayMetrics()), 0, 0, 0);
        } else {
            layoutParams = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
        }
        layoutParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(circle);

        return imageView;
    }

//    public static boolean isConcurEnabled(Ride ride) {
//        if (Settings.getInstance().getUser().isConcurEnabled() && AppProfile.getInstance().isConcurOn()) {
//            switch (ride.getConcurState()) {
//                case Enums.ConcurState.ENABLED:
//                    return true;
//                default:
//                    return false;
//            }
//        } else {
//            return false;
//        }
//    }

    public static boolean isActivityDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity == null || activity.isDestroyed();
        } else {
            return false;
        }
    }

//    public static void showFtueBubble(final BubbleFrameLayout bubbleLayout, final View anchor, final int source, final int delay, final IBubbleListener listener) {
//        showFtueBubble(true, bubbleLayout, anchor, source, delay, listener);
//
//    }
//
//    public static void showFtueBubble(final boolean condition, final BubbleFrameLayout bubbleLayout, final View anchor, final int source, final int delay, final IBubbleListener listener) {
//        showFtueBubble(condition, bubbleLayout, anchor, source, delay, null, listener);
//
//    }
//
//    public static void showFtueBubble(final boolean condition, final BubbleFrameLayout bubbleLayout, final View anchor, final int source, final int delay, final String text, final IBubbleListener listener) {
//        if (condition) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (!TextUtils.isEmpty(text)) {
//                        bubbleLayout.setManualText(text);
//                    }
//                    bubbleLayout.setVisibility(View.VISIBLE);
//                    bubbleLayout.setBubbleAnchor(anchor);
//                    bubbleLayout.setFtueSource(source);
//                    bubbleLayout.setListener(listener);
//                }
//            }, delay);
//        }
//
//    }
//
//    public static void showLineMiniTour(final FragmentManager fragmentManager, final Handler handler, final CarDivision carDivision, final ILineMiniTour iLineMiniPop) {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(InteropConstants.PARAM_DIVISION, carDivision);
//                LineMiniTourDialog linePromoDialog = new LineMiniTourDialog();
//                linePromoDialog.setListener(iLineMiniPop);
//                linePromoDialog.setArguments(bundle);
//                linePromoDialog.show(fragmentManager,LineMiniTourDialog.FRAGMENT_TAG);
//            }
//        }, 50);
//
//    }

    /*
    private static double DELTA_INDEX[] = {
            0,    0.01, 0.02, 0.04, 0.05, 0.06, 0.07, 0.08, 0.1,  0.11,
            0.12, 0.14, 0.15, 0.16, 0.17, 0.18, 0.20, 0.21, 0.22, 0.24,
            0.25, 0.27, 0.28, 0.30, 0.32, 0.34, 0.36, 0.38, 0.40, 0.42,
            0.44, 0.46, 0.48, 0.5,  0.53, 0.56, 0.59, 0.62, 0.65, 0.68,
            0.71, 0.74, 0.77, 0.80, 0.83, 0.86, 0.89, 0.92, 0.95, 0.98,
            1.0,  1.06, 1.12, 1.18, 1.24, 1.30, 1.36, 1.42, 1.48, 1.54,
            1.60, 1.66, 1.72, 1.78, 1.84, 1.90, 1.96, 2.0,  2.12, 2.25,
            2.37, 2.50, 2.62, 2.75, 2.87, 3.0,  3.2,  3.4,  3.6,  3.8,
            4.0,  4.3,  4.7,  4.9,  5.0,  5.5,  6.0,  6.5,  6.8,  7.0,
            7.3,  7.5,  7.8,  8.0,  8.4,  8.7,  9.0,  9.4,  9.6,  9.8,
            10.0
    };


    public static void adjustContrast(ColorMatrix cm, int value) {
        value = (int)cleanValue(value,100);
        if (value == 0) {
            return;
        }
        float x;
        if (value < 0) {
            x = 127 + value / 100*127;
        } else {
            x = value % 1;
            if (x == 0) {
                x = (float)DELTA_INDEX[value];
            } else {
                //x = DELTA_INDEX[(p_val<<0)]; // this is how the IDE does it.
                x = (float)DELTA_INDEX[(value<<0)]*(1-x) + (float)DELTA_INDEX[(value<<0)+1] * x; // use linear interpolation for more granularity.
            }
            x = x*127+127;
        }

        float[] mat = new float[]
                {
                        x/127,0,0,0, 0.5f*(127-x),
                        0,x/127,0,0, 0.5f*(127-x),
                        0,0,x/127,0, 0.5f*(127-x),
                        0,0,0,1,0,
                        0,0,0,0,1
                };
        cm.postConcat(new ColorMatrix(mat));

    }

    protected static float cleanValue(float p_val, float p_limit)
    {
        return Math.min(p_limit, Math.max(-p_limit, p_val));
    }
    */
}

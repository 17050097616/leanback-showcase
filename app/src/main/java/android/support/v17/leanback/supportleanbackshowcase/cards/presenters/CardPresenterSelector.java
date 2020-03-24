/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package android.support.v17.leanback.supportleanbackshowcase.cards.presenters;

import android.content.Context;
import android.support.v17.leanback.supportleanbackshowcase.R;
import android.support.v17.leanback.supportleanbackshowcase.models.Card;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import java.util.HashMap;

/**
 * This PresenterSelector will decide what Presenter to use depending on a given card's type.
 */
public class CardPresenterSelector extends PresenterSelector {

    private final Context mContext;
    private final HashMap<Card.Type, Presenter> presenters = new HashMap<Card.Type, Presenter>();

    public CardPresenterSelector(Context context) {
        mContext = context;
    }

    @Override
    public Presenter getPresenter(Object item) {
        if (!(item instanceof Card)) throw new RuntimeException(
                String.format("The PresenterSelector only supports data items of type '%s'",
                        Card.class.getName()));
        Card card = (Card) item;
        Presenter presenter = presenters.get(card.getType());
        if (presenter == null) {
            switch (card.getType()) {
                case SINGLE_LINE://CardExampleFragment ImageCardView Customize Style
                    presenter = new SingleLineCardPresenter(mContext);
                    break;
                case VIDEO_GRID:
                    presenter = new VideoCardViewPresenter(mContext, R.style.VideoGridCardTheme);
                    break;
                case MOVIE:
                case MOVIE_BASE:
                case MOVIE_COMPLETE:
                case SQUARE_BIG:
                case GRID_SQUARE:
                case GAME: {
                    int themeResId = R.style.MovieCardSimpleTheme;//CardExampleFragment card type==MOVIE
                    if (card.getType() == Card.Type.MOVIE_BASE) {
                        themeResId = R.style.MovieCardBasicTheme;//CardExampleFragment 第二行
                    } else if (card.getType() == Card.Type.MOVIE_COMPLETE) {
                        themeResId = R.style.MovieCardCompleteTheme;//CardExampleFragment 第三行
                    } else if (card.getType() == Card.Type.SQUARE_BIG) {
                        themeResId = R.style.SquareBigCardTheme;//CardExampleFragment 第4行
                    } else if (card.getType() == Card.Type.GRID_SQUARE) {//
                        themeResId = R.style.GridCardTheme;
                    } else if (card.getType() == Card.Type.GAME) {//CardExampleFragment title+mDescription+icon
                        themeResId = R.style.GameCardTheme;
                    }
                    presenter = new ImageCardViewPresenter(mContext, themeResId);
                    break;
                }
                case SIDE_INFO:
                    presenter = new SideInfoCardPresenter(mContext);//CardExampleFragment baseCardView info on the right// 选中左边第三行时
                    break;
                case TEXT:
                    presenter = new TextCardPresenter(mContext);
                    break;
                case ICON:
                    presenter = new IconCardPresenter(mContext);
                    break;
                case CHARACTER:
                    presenter = new CharacterCardPresenter(mContext);//CardExampleFragment
                    break;
                default:
                    presenter = new ImageCardViewPresenter(mContext);//首页的 CardExampleFragment 第6行
                    break;
            }
        }
        presenters.put(card.getType(), presenter);
        return presenter;
    }

}

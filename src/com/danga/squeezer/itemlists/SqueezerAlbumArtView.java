/*
 * Copyright (c) 2011 Kurt Aaholst <kaaholst@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.danga.squeezer.itemlists;


import org.acra.ErrorReporter;

import android.os.RemoteException;
import android.util.Log;
import android.widget.ImageView;

import com.danga.squeezer.R;
import com.danga.squeezer.framework.SqueezerArtworkItem;
import com.danga.squeezer.framework.SqueezerItem;
import com.danga.squeezer.framework.SqueezerItemListActivity;
import com.danga.squeezer.service.ISqueezeService;

public abstract class SqueezerAlbumArtView<T extends SqueezerItem> extends SqueezerIconicItemView<T> {
	public SqueezerAlbumArtView(SqueezerItemListActivity activity) {
		super(activity);
	}

	protected void updateAlbumArt(final ImageView icon, final SqueezerArtworkItem item) {
		icon.setImageResource(R.drawable.icon_album_noart);
		updateIcon(icon, item, getAlbumArtUrl(item.getArtwork_track_id()));
	}

	private String getAlbumArtUrl(String artwork_track_id) {
		if (artwork_track_id == null)
			return null;

		ISqueezeService service = getActivity().getService();
		if (service == null)
			return null;

		try {
			return service.getAlbumArtUrl(artwork_track_id);
		} catch (RemoteException e) {
            ErrorReporter.getInstance().handleException(e);
			Log.e(getClass().getSimpleName(), "Error requesting album art url: " + e);
			return null;
		}
	}

}
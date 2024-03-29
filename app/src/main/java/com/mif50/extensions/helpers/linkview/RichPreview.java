package com.mif50.extensions.helpers.linkview;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.webkit.URLUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Created by BeinMedia @2018.
 */

class RichPreview {

    private MetaData metaData;
    private ResponseListener responseListener;
    private String url;

    RichPreview(ResponseListener responseListener) {
        this.responseListener = responseListener;
        metaData = new MetaData();
    }

    void getPreview(String url){
        this.url = url;
        new getData().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class getData extends AsyncTask<Void , Void , Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Document doc;
            try {
                doc = Jsoup.connect(url)
                        .timeout(30*1000)
                        .get();

                Elements elements = doc.getElementsByTag("meta");

                String title = doc.select("meta[property=og:title]").attr("content");

                if(title == null || title.isEmpty()) {
                    title = doc.title();
                }
                metaData.setTitle(title);

                //getDescription
                String description = doc.select("meta[name=description]").attr("content");
                if (description.isEmpty()) {
                    description = doc.select("meta[name=Description]").attr("content");
                }
                if (description.isEmpty()) {
                    description = doc.select("meta[property=og:description]").attr("content");
                }
                if (description.isEmpty()) {
                    description = "";
                }
                metaData.setDescription(description);


                // getMediaType
                Elements mediaTypes = doc.select("meta[name=medium]");
                String type = "";
                if (mediaTypes.size() > 0) {
                    String media = mediaTypes.attr("content");

                    type = media.equals("image") ? "photo" : media;
                } else {
                    type = doc.select("meta[property=og:type]").attr("content");
                }
                metaData.setMediatype(type);


                //getImages
                Elements imageElements = doc.select("meta[property=og:image]");
                if(imageElements.size() > 0) {
                    String image = imageElements.attr("content");
                    if(!image.isEmpty()) {
                        metaData.setImageurl(resolveURL(url, image));
                    }
                }
                if(metaData.getImageurl().isEmpty()) {
                    String src = doc.select("link[rel=image_src]").attr("href");
                    if (!src.isEmpty()) {
                        metaData.setImageurl(resolveURL(url, src));
                    } else {
                        src = doc.select("link[rel=apple-touch-icon]").attr("href");
                        if(!src.isEmpty()) {
                            metaData.setImageurl(resolveURL(url, src));
                            metaData.setFavicon(resolveURL(url, src));
                        } else {
                            src = doc.select("link[rel=icon]").attr("href");
                            if(!src.isEmpty()) {
                                metaData.setImageurl(resolveURL(url, src));
                                metaData.setFavicon(resolveURL(url, src));
                            }
                        }
                    }
                }

                //Favicon
                String src = doc.select("link[rel=apple-touch-icon]").attr("href");
                if(!src.isEmpty()) {
                    metaData.setFavicon(resolveURL(url, src));
                } else {
                    src = doc.select("link[rel=icon]").attr("href");
                    if(!src.isEmpty()) {
                        metaData.setFavicon(resolveURL(url, src));
                    }
                }

                for(Element element : elements) {
                    if(element.hasAttr("property")) {
                        String str_property = element.attr("property").toString().trim();
                        if(str_property.equals("og:url")) {
                            metaData.setUrl(element.attr("content").toString());
                        }
                        if(str_property.equals("og:site_name")) {
                            metaData.setSitename(element.attr("content").toString());
                        }
                    }
                }

                if(metaData.getUrl().equals("") || metaData.getUrl().isEmpty()) {
                    URI uri = null;
                    try {
                        uri = new URI(url);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    if(url == null) {
                        metaData.setUrl(url);
                    } else {
                        metaData.setUrl(Objects.requireNonNull(uri).getHost());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                responseListener.onError(new Exception("No Html Received from " + url + " Check your Internet " + e.getLocalizedMessage()));
            }catch (RuntimeException ignored) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            responseListener.onData(metaData);
        }
    }

    private String resolveURL(String url, String part) {
        if(URLUtil.isValidUrl(part)) {
            return part;
        } else {
            URI base_uri = null;
            try {
                base_uri = new URI(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            base_uri = Objects.requireNonNull(base_uri).resolve(part);
            return base_uri.toString();
        }
    }

}

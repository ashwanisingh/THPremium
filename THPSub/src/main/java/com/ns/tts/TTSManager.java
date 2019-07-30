package com.ns.tts;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.util.Log;


import com.netoperation.model.RecoBean;
import com.ns.thpremium.BuildConfig;
import com.ns.utils.TextUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TTSManager {

    private boolean mIsTTSInitialised;

    private TextToSpeech textToSpeech;

    private static TTSManager sTTSManager;

    private TTSCallbacks mCallback;


    private final String TAG = "Ashwani";

    public static TTSManager getInstance() {
        if(sTTSManager == null) {
            sTTSManager = new TTSManager();
        }

        return sTTSManager;
    }

    private TTSManager() {
    }

    public void init(final Context context, TTSCallbacks callbacks) {
        mCallback = callbacks;
            textToSpeech =new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status == TextToSpeech.SUCCESS) {
                        Locale locale = new Locale(BuildConfig.LOCALE);
                        int result = textToSpeech.setLanguage(locale);

                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e(TAG, "This Language is not supported");
                            mCallback.onTTSError(result);
                            return;
                        }

                        if( Build.VERSION.SDK_INT  >= 21 ) {
                            Voice voice = textToSpeech.getVoice();
                            Set<String> features = voice.getFeatures();
                            if (features.contains(TextToSpeech.Engine.KEY_FEATURE_NOT_INSTALLED)) {
                                //Voice data needs to be downloaded
                                mCallback.onTTSError(1000);
                                return;
                            }
                        }

                        mCallback.onTTSInitialized();
                        mIsTTSInitialised = true;

                        if( Build.VERSION.SDK_INT  >= 15 ) {
                            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                                @Override
                                public void onStart(String s) {
                                    Log.i(TAG, "onStart :: "+s);


                                }

                                @Override
                                public void onDone(String s) {
                                    Log.i(TAG, "onDone :: "+s);

                                }

                                @Override
                                public void onError(String s) {
                                    Log.i(TAG, "onError :: "+s);
                                    mCallback.onTTSError(1001);
                                }
                            });
                        }
                        else {
                            textToSpeech.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                                @Override
                                public void onUtteranceCompleted(String s) {

                                    Log.i(TAG, "onUtteranceCompleted :: "+s);


                                }
                            });
                        }

                    } else {
                        if(mCallback != null) {
                            mCallback.onTTSError(status);
                        }
                    }
                }
            });

    }


    public void speakSpeech(RecoBean articlesBean) {

        String speakableText = TextUtil.speakableText(articlesBean.getArticletitle(), "", articlesBean.getDescription());

//        speakableText = "संजय जाधव दिग्दर्शित 'लकी' चित्रपटाचे फस्ट लूक पोस्टर नुकतेच सोशल मीडियावरून रिलीज झाले. 'लकी' सिनेमा 7 फेब्रुवारी 2019 ला रिलीज होणार आहे\n";
//        speakableText = "নদিয়াতে প্রশাসনিক সভায় অংশ নিতে গিয়ে বৃহস্পতিবার একের পর এক বিষয়ে নরেন্দ্র মোদী সরকারকে একহাত নিলেন মমতা বন্দ্যোপাধ্যায়।";

        List<String> parts = TextUtil.splitOnPunctuation(speakableText);
        int count = 0;
        String utterenceId = utterenceId(articlesBean.getArticleId(), count);

        for (String str : parts) {

            HashMap<String, String> myHashRender = new HashMap();
            myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utterenceId);

            if (count == 0) { // Use for the first splited text to flush on audio stream
                textToSpeech.speak(str.trim(),TextToSpeech.QUEUE_FLUSH, myHashRender);
            } else { // add the new test on previous then play the TTS
                textToSpeech.speak(str.trim(), TextToSpeech.QUEUE_ADD, myHashRender);
            }

            if(mCallback != null) {
                mCallback.onTTSPlayStarted(count);
            }

            textToSpeech.playSilence(750, TextToSpeech.QUEUE_ADD, null);

            count++;
        }

    }


    private String utterenceId(String articleId, int count) {
        return articleId+"_"+count;
    }

    public void setLanguage(Locale locale) {
        textToSpeech.setLanguage(locale);
    }


    public Set<Locale> getAvailableLanguages() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return textToSpeech.getAvailableLanguages();
        } else {
            Locale de = textToSpeech.getLanguage();
            Set<Locale> deSet = new HashSet<>();
            deSet.add(de);
            return deSet;
        }
    }

    public int getMaxSpeechInputLength() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return textToSpeech.getMaxSpeechInputLength();
        } else {
            return 400;
        }
    }

    public Set<Voice> getVoices() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return textToSpeech.getVoices();
        }

        return new HashSet<Voice>();
    }


    public boolean isTTSInitialized() {
        return mIsTTSInitialised;
    }


    public boolean isTTSPlaying() {
        return textToSpeech.isSpeaking();
    }


    public void stopTTS() {
        if(isTTSInitialized() && isTTSPlaying()) {
            textToSpeech.stop();
        }
    }



    public void release() {
        if(textToSpeech != null) {

            mIsTTSInitialised = false;
            // Stop the TextToSpeech Engine
            textToSpeech.stop();
            // Shutdown the TextToSpeech Engine
            textToSpeech.shutdown();

        }


    }
}

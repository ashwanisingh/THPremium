package com.netoperation.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface BookmarkTableDao {

    @Insert(onConflict = REPLACE)
    void insertBookmark(BookmarkTable bookmarkTable);

    @Query("SELECT * FROM BookmarkTable")
    List<BookmarkTable> getAllBookmark();

    @Query("SELECT * FROM BookmarkTable WHERE aid = :aid")
    Flowable<BookmarkTable> getBookmarkArticle(String aid);

    @Query("SELECT * FROM BookmarkTable WHERE aid = :aid")
    List<BookmarkTable> getBookmarkArticles(String aid);

    @Query("DELETE FROM BookmarkTable WHERE aid = :aid")
    int deleteBookmarkArticle(String aid);

    @Query("DELETE FROM BookmarkTable")
    void deleteAll();
}

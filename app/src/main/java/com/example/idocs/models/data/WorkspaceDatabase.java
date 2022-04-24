package com.example.idocs.models.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.idocs.R;
import com.example.idocs.models.AppDao;

@Database(entities = {Workspace.class, Group.class, Document.class}, version = 1)
public abstract class WorkspaceDatabase extends RoomDatabase{
    private static WorkspaceDatabase instance;
    public abstract AppDao workspaceDao();

    public static synchronized WorkspaceDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WorkspaceDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private AppDao appDao;

        private PopulateDbAsyncTask(WorkspaceDatabase db) {
            appDao = db.workspaceDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDao.insertWorkspace(new Workspace("Second Semester", R.drawable.ic_workspace));
            appDao.insertWorkspace(new Workspace("Third Semester", R.drawable.ic_workspace));
            appDao.insertWorkspace(new Workspace("First Semester", R.drawable.ic_workspace));

            appDao.insertGroup(new Group("Bel", 1));
            appDao.insertGroup(new Group("OOP", 1));
            appDao.insertGroup(new Group("Dsa", 2));

            appDao.insertDocument(new Document("Chapter1", "chapter1", 1));
            appDao.insertDocument(new Document("Chapter2", "chapter2", 1));
            appDao.insertDocument(new Document("Chapter3", "chapter3", 1));
            appDao.insertDocument(new Document("Chapter1", "chapter1", 2));
//            workspaceDao.insertGroup(new Group("OOP", 1));
//            workspaceDao.insertGroup(new Group("Dsa", 2));
            return null;
        }
    }
}

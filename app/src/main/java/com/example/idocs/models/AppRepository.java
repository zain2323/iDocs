package com.example.idocs.models;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.idocs.models.data.Document;
import com.example.idocs.models.data.Group;
import com.example.idocs.models.data.GroupWithDocuments;
import com.example.idocs.models.data.User;
import com.example.idocs.models.data.Workspace;
import com.example.idocs.models.data.WorkspaceDatabase;
import com.example.idocs.models.data.WorkspaceWithGroup;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AppRepository {
    private AppDao appDao;
    private LiveData<List<Workspace>> allWorkspaces;

    public AppRepository(Application application) {
        WorkspaceDatabase workspaceDatabase = WorkspaceDatabase.getInstance(application);
        appDao = workspaceDatabase.workspaceDao();
        allWorkspaces = appDao.getAllWorkspaces();
    }

    public void insertWorkspace(Workspace workspace) {
        new InsertWorkspaceAsyncTask(appDao).execute(workspace);
    }

    public void updateWorkspace(Workspace workspace) {
        new UpdateWorkspaceAsyncTask(appDao).execute(workspace);
    }

    public void deleteWorkspace(Workspace workspace) {
        new DeleteWorkspaceAsyncTask(appDao).execute(workspace);
    }

    public LiveData<List<Workspace>> getAllWorkspaces() {
        return allWorkspaces;
    }

    public void deleteAllWorkspaces() {
        new DeleteAllWorkspacesAsyncTask(appDao).execute();
    }

//    Current user methods
    public void createUser(User user) {
        new CreateUserAsyncTask(appDao).execute(user);
    }

    public void updateUser(User user) {
        new UpdateUserAsyncTask(appDao).execute(user);
    }

//    public LiveData<List<User>> getUserBySessionId(String sessionId) {
//        return appDao.getUserBySessionId(sessionId);
//    }
//
//    public LiveData<List<User>> getUserById(String userId) {
//        return appDao.getUserById(userId);
//    }

    //    Groups Methods
    public long insertGroup(Group group) throws ExecutionException, InterruptedException {
        long id = new InsertGroupAsyncTask(appDao).execute(group).get();
        return id;
    }

    public void updateGroup(Group group) {
        new UpdateGroupAsyncTask(appDao).execute(group);
    }

    public void deleteGroup(Group group) {
        new DeleteGroupAsyncTask(appDao).execute(group);
    }

    public LiveData<List<WorkspaceWithGroup>> getWorkspaceWithGroups(int workspaceId) {
        return appDao.getWorkspaceWithGroups(workspaceId);
    }

    //    Documents Methods
    public void insertDocument(Document document) {
        new InsertDocumentAsyncTask(appDao).execute(document);
    }

    public void updateDocument(Document document) {
        new UpdateDocumentAsyncTask(appDao).execute(document);
    }

    public void deleteDocument(Document document) {
        new DeleteDocumentAsyncTask(appDao).execute(document);
    }

    public LiveData<List<GroupWithDocuments>> getGroupWithDocuments(int groupId) {
        return appDao.getGroupWithDocuments(groupId);
    }

    //    Workspace Async Tasks
    private static class InsertWorkspaceAsyncTask extends AsyncTask<Workspace, Void, Void> {
        private AppDao appDao;

        public InsertWorkspaceAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Workspace... workspaces) {
            appDao.insertWorkspace(workspaces[0]);
            return null;
        }
    }

    private static class UpdateWorkspaceAsyncTask extends AsyncTask<Workspace, Void, Void> {
        private AppDao appDao;

        public UpdateWorkspaceAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Workspace... workspaces) {
            appDao.updateWorkspace(workspaces[0]);
            return null;
        }
    }


    private static class DeleteWorkspaceAsyncTask extends AsyncTask<Workspace, Void, Void> {
        private AppDao appDao;

        public DeleteWorkspaceAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Workspace... workspaces) {
            appDao.deleteWorkspace(workspaces[0]);
            return null;
        }
    }


    private static class DeleteAllWorkspacesAsyncTask extends AsyncTask<Void, Void, Void> {
        private AppDao appDao;

        public DeleteAllWorkspacesAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDao.deleteAllWorkspaces();
            return null;
        }
    }

//    Current user Async Tasks

    private static class CreateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private AppDao appDao;

        public CreateUserAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            appDao.createUser(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private AppDao appDao;

        public UpdateUserAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            appDao.updateUser(users[0]);
            return null;
        }
    }

//    Groups Async Tasks

    private static class InsertGroupAsyncTask extends AsyncTask<Group, Void, Long> {
        private AppDao appDao;

        public InsertGroupAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Long doInBackground(Group... groups) {
            long id = appDao.insertGroup(groups[0]);
            return id;
        }
    }

    private static class UpdateGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private AppDao appDao;

        public UpdateGroupAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            appDao.updateGroup(groups[0]);
            return null;
        }
    }

    private static class DeleteGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private AppDao appDao;

        public DeleteGroupAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            appDao.deleteGroup(groups[0]);
            return null;
        }
    }

//    Document Async Tasks

    private static class InsertDocumentAsyncTask extends AsyncTask<Document, Void, Void> {
        private AppDao appDao;

        public InsertDocumentAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Document... documents) {
            appDao.insertDocument(documents[0]);
            return null;
        }
    }

    private static class UpdateDocumentAsyncTask extends AsyncTask<Document, Void, Void> {
        private AppDao appDao;

        public UpdateDocumentAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Document... documents) {
            appDao.updateDocument(documents[0]);
            return null;
        }
    }

    private static class DeleteDocumentAsyncTask extends AsyncTask<Document, Void, Void> {
        private AppDao appDao;

        public DeleteDocumentAsyncTask(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Document... documents) {
            appDao.deleteDocument(documents[0]);
            return null;
        }
    }
}


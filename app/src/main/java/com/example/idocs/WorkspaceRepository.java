package com.example.idocs;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkspaceRepository
{
    private WorkspaceDao workspaceDao;
    private LiveData<List<Workspace>> allWorkspaces;

    public WorkspaceRepository(Application application)
    {
        WorkspaceDatabase workspaceDatabase = WorkspaceDatabase.getInstance(application);
        workspaceDao = workspaceDatabase.workspaceDao();
        allWorkspaces = workspaceDao.getAllWorkspaces();
    }

    public void insertWorkspace(Workspace workspace)
    {
        new InsertWorkspaceAsyncTask(workspaceDao).execute(workspace);
    }

    public void updateWorkspace(Workspace workspace)
    {
        new UpdateWorkspaceAsyncTask(workspaceDao).execute(workspace);
    }

    public void deleteWorkspace(Workspace workspace)
    {
        new DeleteWorkspaceAsyncTask(workspaceDao).execute(workspace);
    }

    public void deleteAllWorkspaces()
    {
        new DeleteAllWorkspacesAsyncTask(workspaceDao).execute();
    }

//    Groups Methods
    public LiveData<List<Workspace>> getAllWorkspaces()
    {
        return allWorkspaces;
    }

    public long insertGroup(Group group) throws ExecutionException, InterruptedException {
        long id = new InsertGroupAsyncTask(workspaceDao).execute(group).get();
        return id;
    }

    public void updateGroup(Group group)
    {
        new UpdateGroupAsyncTask(workspaceDao).execute(group);
    }

    public void deleteGroup(Group group)
    {
        new DeleteGroupAsyncTask(workspaceDao).execute(group);
    }

    public LiveData<List<WorkspaceWithGroup>> getWorkspaceWithGroups(int workspaceId)
    {
        return workspaceDao.getWorkspaceWithGroups(workspaceId);
    }

//    Documents Methods
public void insertDocument(Document document)
{
    new InsertDocumentAsyncTask(workspaceDao).execute(document);
}

    public void updateDocument(Document document)
    {
        new UpdateDocumentAsyncTask(workspaceDao).execute(document);
    }

    public void deleteDocument(Document document)
    {
        new DeleteDocumentAsyncTask(workspaceDao).execute(document);
    }

    public LiveData<List<GroupWithDocuments>> getGroupWithDocuments(int groupId)
    {
        return workspaceDao.getGroupWithDocuments(groupId);
    }

//    Workspace Async Tasks
    private static class InsertWorkspaceAsyncTask extends AsyncTask<Workspace, Void, Void>
    {
        private WorkspaceDao workspaceDao;

        public InsertWorkspaceAsyncTask(WorkspaceDao workspaceDao) {
            this.workspaceDao = workspaceDao;
        }

        @Override
        protected Void doInBackground(Workspace... workspaces) {
            workspaceDao.insertWorkspace(workspaces[0]);
            return null;
        }
    }

    private static class UpdateWorkspaceAsyncTask extends AsyncTask<Workspace, Void, Void>
    {
        private WorkspaceDao workspaceDao;

        public UpdateWorkspaceAsyncTask(WorkspaceDao workspaceDao) {
            this.workspaceDao = workspaceDao;
        }

        @Override
        protected Void doInBackground(Workspace... workspaces) {
            workspaceDao.updateWorkspace(workspaces[0]);
            return null;
        }
    }


    private static class DeleteWorkspaceAsyncTask extends AsyncTask<Workspace, Void, Void>
    {
        private WorkspaceDao workspaceDao;

        public DeleteWorkspaceAsyncTask(WorkspaceDao workspaceDao) {
            this.workspaceDao = workspaceDao;
        }

        @Override
        protected Void doInBackground(Workspace... workspaces) {
            workspaceDao.deleteWorkspace(workspaces[0]);
            return null;
        }
    }


    private static class DeleteAllWorkspacesAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private WorkspaceDao workspaceDao;

        public DeleteAllWorkspacesAsyncTask(WorkspaceDao workspaceDao) {
            this.workspaceDao = workspaceDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            workspaceDao.deleteAllWorkspaces();
            return null;
        }
    }

//    Groups Async Tasks

    private static class InsertGroupAsyncTask extends AsyncTask<Group, Void, Long>
    {
        private WorkspaceDao workspaceDao;

        public InsertGroupAsyncTask(WorkspaceDao workspaceDao) {
            this.workspaceDao = workspaceDao;
        }

        @Override
        protected Long doInBackground(Group... groups) {
            long id = workspaceDao.insertGroup(groups[0]);
            return id;
        }
    }

    private static class UpdateGroupAsyncTask extends AsyncTask<Group, Void, Void>
    {
        private WorkspaceDao workspaceDao;

        public UpdateGroupAsyncTask(WorkspaceDao workspaceDao) {
            this.workspaceDao = workspaceDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            workspaceDao.updateGroup(groups[0]);
            return null;
        }
    }

    private static class DeleteGroupAsyncTask extends AsyncTask<Group, Void, Void>
    {
        private WorkspaceDao workspaceDao;

        public DeleteGroupAsyncTask(WorkspaceDao workspaceDao) {
            this.workspaceDao = workspaceDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            workspaceDao.deleteGroup(groups[0]);
            return null;
        }
    }

//    Document Async Tasks

    private static class InsertDocumentAsyncTask extends AsyncTask<Document, Void, Void>
    {
        private WorkspaceDao workspaceDao;

        public InsertDocumentAsyncTask(WorkspaceDao workspaceDao) {
            this.workspaceDao = workspaceDao;
        }

        @Override
        protected Void doInBackground(Document... documents) {
            workspaceDao.insertDocument(documents[0]);
            return null;
        }
    }

    private static class UpdateDocumentAsyncTask extends AsyncTask<Document, Void, Void>
    {
        private WorkspaceDao workspaceDao;

        public UpdateDocumentAsyncTask(WorkspaceDao workspaceDao) {
            this.workspaceDao = workspaceDao;
        }

        @Override
        protected Void doInBackground(Document... documents) {
            workspaceDao.updateDocument(documents[0]);
            return null;
        }
    }

    private static class DeleteDocumentAsyncTask extends AsyncTask<Document, Void, Void>
    {
        private WorkspaceDao workspaceDao;

        public DeleteDocumentAsyncTask(WorkspaceDao workspaceDao) {
            this.workspaceDao = workspaceDao;
        }

        @Override
        protected Void doInBackground(Document... documents) {
            workspaceDao.deleteDocument(documents[0]);
            return null;
        }
    }
}

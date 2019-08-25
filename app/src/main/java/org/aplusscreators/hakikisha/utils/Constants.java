package org.aplusscreators.hakikisha.utils;

public class Constants {

    public static final String FORM_ON_EDIT_KEY = "form_open_on_edit";
    public static final String CUSTOMER_ACCOUNT_TYPE = "customer_account_type";
    public static final String SELLER_ACCOUNT_TYPE = "seller_account_type";

    public static final class CAMERA {
        public static final int IMAGE_CAPTURE_REQUEST_CODE = 2120;
    }

    public static final class PERMISSION_CONSTANTS {
        public static final int CAMERA_REQUEST_CODE = 2121;
    }

    public static final class DATA_CONTANTS {
        public static final String SCHEDULE_DATA_TITLE_KEY = "schedule_data_key";
        public static final String SCHEDULE_DATA_LOCATION_KEY = "schedule_location_data_key";
        public static final String SCHEDULE_DATA_START_TIME_DATA_KEY = "schedule_start_time_data_key";
        public static final String SCHEDULE_DATA_END_TIEM_KEY = "schedule_end_time_key";
        public static final String SCHEDULE_DATA_DATE_KEY = "schedule_date_key";
        public static final String SCHEDULE_DATA_ROOM_KEY = "schedule_room";
        public static final String SCHEDULE_DATA_PEOPLE_STRING_KEY = "schedule_people_key";
        public static final String SCHEDULE_DATA_NOTE_KEY = "schedul_notes_key";
        public static final String SCHEDULE_DATA_UUID = "schedule_uuid_key";
        public static final String TASK_LIST_UUID_KEY = "task_list_uuid_key";
        public static final String CHECKLIST_TITLE_KEY = "checklist_title_key";
        public static final String CHECKLIST_UUID = "checklist_uuid";

        public static final class SCHEDULED_REMINDER_WORKER_CONSTANTS {
            public static final String SCHEDULE_DATA_NOTIFICATION_TITLE_KEY = "notification_title_key";
            public static final String SCHEDULE_DATA_NOTIFICATION_DESCRIPTION_KEY = "notification_description_key";
            public static final String SCHEDULE_DATA_NOTIFICATION_ACTION_KEY = "notification_action_key";
            public static final String SCHEDULE_DATA_USER_NAME_KEY = "user_name_data_key";
        }

        public static final class TIME_DURATION_TIME_CONSTANTS {
            public static final String STANDARD_DATA_FORMART = "EEE MMM dd, yyyy";
            public static final String STANDARD_DATE_DISPLAY_FORMAT = "EEE MMM dd, yyyy";
        }

    }

    public static final class TASK_DATA {
        public static final int TASK_COMPLETED = 1;
        public static final int TASK_INCOMPLETE = 0;
    }



}

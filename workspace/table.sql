CREATE TABLE IF NOT EXISTS `analytics.user_events` (

    `event_date` DATE NULLABLE OPTIONS(description="Event date"),

    `user_id` STRING REQUIRED OPTIONS(description="User ID"),

    `event_type` STRING NULLABLE OPTIONS(description="User behavior events"),

    `properties` JSON NULLABLE OPTIONS(description="User behavior events")

    )

    PARTITION BY event_date


    CLUSTER BY user_id, event_type

    OPTIONS(
               description="User behavior events"
           );
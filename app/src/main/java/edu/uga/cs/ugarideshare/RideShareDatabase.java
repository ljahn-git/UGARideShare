package edu.uga.cs.ugarideshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class RideShareDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "uga_rideshare.db";
    public static final int DATABASE_VERSION = 1;
    private static final int PASSWORD_HASH_ITERATIONS = 120_000;
    private static final int PASSWORD_SALT_BYTES = 16;
    private static final int PASSWORD_HASH_BITS = 256;

    public static final String USERS_TABLE = "users";
    public static final String RIDES_TABLE = "rides";

    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static final String RIDE_ID = "ride_id";
    public static final String USER_POSTED = "user_posted";
    public static final String TYPE = "type";
    public static final String PICKUP_LOCATION = "pickup_location";
    public static final String DROPOFF_LOCATION = "dropoff_location";
    public static final String STATUS = "status";
    public static final String USER_ACCEPTED = "user_accepted";

    private static final String RIDE_TYPE_OFFER = "offer";
    private static final String RIDE_TYPE_REQUEST = "request";
    private static final String RIDE_STATUS_OPEN = "open";

    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + USERS_TABLE + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USERNAME + " TEXT NOT NULL UNIQUE CHECK(length(trim(" + USERNAME + ")) > 0), " +
                    EMAIL + " TEXT NOT NULL UNIQUE CHECK(length(trim(" + EMAIL + ")) > 0), " +
                    PASSWORD + " TEXT NOT NULL CHECK(length(" + PASSWORD + ") > 0)" +
                    ")";

    private static final String CREATE_RIDES_TABLE =
            "CREATE TABLE " + RIDES_TABLE + " (" +
                    RIDE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_POSTED + " INTEGER NOT NULL REFERENCES " + USERS_TABLE + "(" + USER_ID + ") ON DELETE CASCADE, " +
                    TYPE + " TEXT NOT NULL CHECK(" + TYPE + " IN ('offer', 'request')), " +
                    PICKUP_LOCATION + " TEXT NOT NULL CHECK(length(trim(" + PICKUP_LOCATION + ")) > 0), " +
                    DROPOFF_LOCATION + " TEXT NOT NULL CHECK(length(trim(" + DROPOFF_LOCATION + ")) > 0), " +
                    STATUS + " TEXT NOT NULL DEFAULT 'open' CHECK(" + STATUS + " IN ('accepted', 'open')), " +
                    USER_ACCEPTED + " INTEGER REFERENCES " + USERS_TABLE + "(" + USER_ID + "), " +
                    "CHECK((" + STATUS + " = 'open' AND " + USER_ACCEPTED + " IS NULL) OR " +
                    "(" + STATUS + " = 'accepted' AND " + USER_ACCEPTED + " IS NOT NULL))" +
                    ")";

    public RideShareDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long registerUser(String username, String email, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty");
        }

        ContentValues values = new ContentValues();
        values.put(USERNAME, username.trim());
        values.put(EMAIL, email.trim());
        values.put(PASSWORD, hashPassword(password));
        return getWritableDatabase().insertOrThrow(USERS_TABLE, null, values);
    }

    public long postRideOffer(long userPosted, String pickupLocation, String dropoffLocation) {
        return insertRide(userPosted, RIDE_TYPE_OFFER, pickupLocation, dropoffLocation);
    }

    public long postRideRequest(long userPosted, String pickupLocation, String dropoffLocation) {
        return insertRide(userPosted, RIDE_TYPE_REQUEST, pickupLocation, dropoffLocation);
    }

    private long insertRide(long userPosted, String type, String pickupLocation, String dropoffLocation) {
        if (userPosted <= 0) {
            throw new IllegalArgumentException("Posting user ID must be positive");
        }
        if (pickupLocation == null || pickupLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("Pickup location must not be empty");
        }
        if (dropoffLocation == null || dropoffLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("Dropoff location must not be empty");
        }

        ContentValues values = new ContentValues();
        values.put(USER_POSTED, userPosted);
        values.put(TYPE, type);
        values.put(PICKUP_LOCATION, pickupLocation.trim());
        values.put(DROPOFF_LOCATION, dropoffLocation.trim());
        values.put(STATUS, RIDE_STATUS_OPEN);
        values.putNull(USER_ACCEPTED);
        return getWritableDatabase().insertOrThrow(RIDES_TABLE, null, values);
    }

    private static String hashPassword(String password) {
        byte[] salt = new byte[PASSWORD_SALT_BYTES];
        new SecureRandom().nextBytes(salt);

        PBEKeySpec keySpec = new PBEKeySpec(
                password.toCharArray(), salt, PASSWORD_HASH_ITERATIONS, PASSWORD_HASH_BITS);
        try {
            byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                    .generateSecret(keySpec)
                    .getEncoded();
            return "pbkdf2_sha1$" + PASSWORD_HASH_ITERATIONS + "$" +
                    Base64.encodeToString(salt, Base64.NO_WRAP) + "$" +
                    Base64.encodeToString(hash, Base64.NO_WRAP);
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Unable to hash the user password", exception);
        } finally {
            keySpec.clearPassword();
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase database) {
        database.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_USERS_TABLE);
        database.execSQL(CREATE_RIDES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        throw new IllegalStateException(
                "No database migration is defined from version " + oldVersion + " to " + newVersion);
    }
}
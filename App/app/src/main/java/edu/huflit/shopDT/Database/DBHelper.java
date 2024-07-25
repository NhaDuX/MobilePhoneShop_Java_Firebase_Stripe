package edu.huflit.shopDT.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import java.util.Calendar;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context) {
        super(context, "DBTrungTamTheThao.sqlite", null, 1);
    }

    //Truy vấn không trả kết quả
    //Truy vấn không trả kết quả là truy vấn thêm, xóa, sửa trên database
    public void WriteQuery(String content){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(content);
    }
    //Truy vấn trả kết quả (Select)
    public Cursor GetData(String content){
        SQLiteDatabase db =  getReadableDatabase();
        return db.rawQuery(content, null);
    }
    //Hàm AddRole, Thêm dữ liệu vào bảng "ROLE"

    @Override
    public void onCreate(SQLiteDatabase db) {
        //region Tạo bảng ROLE: Quyền hạn
        db.execSQL("CREATE TABLE IF NOT EXISTS [ROLE] (" +
                "QUYENHAN VARCHAR PRIMARY KEY NOT NULL," +
                "NOIDUNG Text NOT NULL)");
        //Thêm dữ liệu vào bảng [ROLE]
        String s = "Insert into [ROLE] values " +
                "('admin', 'Quản trị viên');";
        db.execSQL("Insert into [ROLE] values" +
                "('admin', 'Quản trị viên')," +
                "('customer', 'Khách hàng')");
        //endregion

        //region Tạo bảng ACCOUNT: chứa các tài khoản
        db.execSQL("CREATE TABLE IF NOT EXISTS ACCOUNT (\n" +
                "\tTAIKHOAN VARCHAR PRIMARY KEY NOT NULL,\n" +
                "\tMATKHAU VARCHAR NOT NULL,\n" +
                "\tTEN VARCHAR,\n" +
                "\tSDT VARCHAR,\n" +
                "\tGMAIL VARCHAR,\n" +
                "\tDIACHI VARCHAR \n" +
                ");");
        //Thêm tài khoản admin và khách hàng mẫu để test
        db.execSQL("Insert into ACCOUNT values " +
                "('123', '123', 'Nguyen Van A', '0924939352', 'voquinamit1@gmail.com', 'thailan'), " +
                "('1234', '1234', 'Nguyen Thi B', '0334379439', '', '119');");
        //endregion

        //region Tạo bảng CATEGORY: Phân loại sân banh
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS [CATEGORY] (" +
                        "NAME VARCHAR PRIMARY KEY NOT NULL, " +
                        "NOIDUNG VARCHAR);"
        );
        //Thêm một số CATEGORY
        db.execSQL("Insert into [CATEGORY] values " +
                "('SAN5', 'Sân 5'), " +
                "('SAN7', 'Sân 7'), " +
                "('SANDANANG', 'Sân đa năng')");
        //endregion

        //region Tạo bảng SANBANH: Lưu trữ sân banh
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS SANBANH (\n" +
                        "MASB VARCHAR PRIMARY KEY NOT NULL,\n" +
                        "TENSB VARCHAR NOT NULL, \n" +
                        "PHANLOAI VARCHAR NOT NULL, \n" +
                        "TINHTRANG VARCHAR, \n" +
                        "SOGIO INTEGER NOT NULL,\n" +
                        "DONGIA REAL CHECK(DONGIA > 0) NOT NULL,\n" +
                        "HINHANH INTEGER NOT NULL,\n" +
                        "FOREIGN KEY (PHANLOAI) REFERENCES [CATEGORY](NAME)" +
                        ");"
        );

        //endregion

        //region Tạo bảng Bill_Detail: Chi tiết hóa đơn
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS BILLDETAIL (\n" +
                        "    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "    MASB VARCHAR NOT NULL,\n" +
                        "    IDORDER   INTEGER not NULL,\n" +
                        "    QUANTITY  INTEGER check(QUANTITY > 0) not NULL,\n" +
                        "    UNITPRICE Real check(UNITPRICE > 0) not NULL,\n" +
                        "    TOTALPRICE Real check (TOTALPRICE > 0) not Null,\n" +
                        "    FOREIGN KEY (MASB) REFERENCES SANBANH(MASB),\n" +
                        "    FOREIGN KEY (IDORDER) REFERENCES BILL(ID) " + ");"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS CARTLIST (\n" +
                        "\tIDCARTLIST   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "\tIDCUS        VARCHAR,\n" +
                        "\tIDSANBANH    VARCHAR NOT NULL,\n" +
                        "\tSOGIO      INTEGER CHECK(SOGIO > 0) NOT NULL," +
                        "\tDONGIA       REAL,\n" +
                        "\tFOREIGN KEY (IDCUS) REFERENCES ACCOUNT(TAIKHOAN),\n" +
                        "\tFOREIGN KEY (IDSANBANH) REFERENCES SANBANH(MASB) \n" +
                        ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i,int i1) {

    }
    private boolean CheckExists(String PrimaryColumn, String TableName){
        Cursor i = this.GetData("Select* from " + TableName);
        while (i.moveToNext()){
            if (PrimaryColumn.equals(i.getString(0))){
                return false;
            }
        }
        return true;
    }
    public boolean AddAccount(String taikhoan, String matkhau, String ten, String sdt, String gmail, String diachi){
        boolean check = CheckExists(taikhoan, "Account");
        if (check){
            this.WriteQuery("Insert into ACCOUNT Values" +
                    "('" + taikhoan + "', '" + matkhau + "', '" + ten + "', '" + sdt + "', '" + gmail + "','" + diachi + "');");
        }
        return check;
    }
    public boolean AddRole(String role, String content){
        boolean check = CheckExists(role, "[ROLE]");
        if (check){
            this.WriteQuery("Insert into [ROLE] Values" +
                    "('" + role + "', '" + content + "');");
        }
        return check;
    }
    public boolean AddProduct(String MASB, String TENSB, String PHANLOAI, String TINHTRANG, Integer SOGIO,  double DONGIA, int HINHANH){
        boolean check = CheckExists(MASB, "SANBANH");
        if (check){

            this.WriteQuery("Insert into SANBANH Values" +
                    "('" + MASB + "', '" + TENSB + "', '" + PHANLOAI + "','" + TINHTRANG + "', '" + SOGIO + "', '" + DONGIA + "', '" + HINHANH + "');");
        }
        return check;
    }
    public boolean AddCategory(String NAME, String CONTENT){
        boolean check = CheckExists(NAME, "[CATEGORY]");
        if (check){
            this.WriteQuery("Insert into [CATEGORY] Values" +
                    "('" + NAME + "', '" + CONTENT + "');");
        }
        return check;
    }
    public boolean AddBill(String TAIKHOANCUS, String ADDRESSDELIVERRY) {
        try {
            Calendar c = Calendar.getInstance();
            String DATEORDER = Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(c.get(Calendar.MONTH) + 1) + "/" + Integer.toString(c.get(Calendar.YEAR));
            SQLiteDatabase db = getReadableDatabase();
            this.WriteQuery("Insert into [CATEGORY] (DATEORDER, TAIKHOANCUS, ADDRESSDELIVERRY) Values" +
                    "('" + DATEORDER + "', '" + TAIKHOANCUS + "', '" + ADDRESSDELIVERRY + "');");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

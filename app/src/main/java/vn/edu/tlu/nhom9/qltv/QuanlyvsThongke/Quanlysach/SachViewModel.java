package vn.edu.tlu.nhom9.qltv.QuanlyvsThongke.Quanlysach;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import vn.edu.tlu.nhom9.qltv.LOPDAO.SachDao;
import vn.edu.tlu.nhom9.qltv.LOPPRODUCT.Sach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SachViewModel extends AndroidViewModel {
    SachDao dao;
    MutableLiveData<List<Sach>> liveData;

    public SachViewModel(@NonNull @NotNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        dao = new SachDao(application);
    }

    public MutableLiveData<List<Sach>> getLiveData() {
        loads();
        return liveData;
    }

    private void loads() {
        List<Sach> list = new ArrayList<>();
        list = dao.GETS();
        liveData.setValue(list);
    }

//    public long addSach(Sach sach) {
//        long kq = dao.ADDS(sach);
//        if (kq > 0) {
//            loads();
//            return kq;
//        }
//        return -1;
//    }
}
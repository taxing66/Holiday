package com.joiway.devin.holiday.model.single;

import com.joiway.devin.holiday.model.CrimesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 德华 on 2016/8/2.
 */
public class CrimesBeanLab {
    private static CrimesBeanLab sCrimesBeanLab;
    private List<CrimesBean> mCrimesBeanList = new ArrayList<>();
    private CrimesBeanLab(){
        for (int i = 0; i < 100; i++) {
            CrimesBean crimesBean = new CrimesBean();
            crimesBean.setTitle(i+"#crimesBean");
            addCrimes(crimesBean);
        }

    }
    public static CrimesBeanLab getCrimesBeanLab(){
        if (sCrimesBeanLab==null){
            synchronized (CrimesBeanLab.class){
                if (sCrimesBeanLab==null){
                    sCrimesBeanLab = new CrimesBeanLab();
                }
            }
        }
        return  sCrimesBeanLab;
    }

    public void addCrimes(CrimesBean crimesBean){
        mCrimesBeanList.add(crimesBean);
    }
    public  List<CrimesBean> getCrimesBeanList(){
        return mCrimesBeanList;
    }

}

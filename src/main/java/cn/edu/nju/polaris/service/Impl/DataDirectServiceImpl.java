package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.service.CashPoolService;
import cn.edu.nju.polaris.service.DataDirectService;
import cn.edu.nju.polaris.vo.Inventory.*;
import cn.edu.nju.polaris.vo.SupplyModuleOne;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataDirectServiceImpl implements DataDirectService {

    private final CashPoolService cashPoolService;

    public DataDirectServiceImpl(CashPoolService cashPoolService) {
        this.cashPoolService = cashPoolService;
    }

    private static final double SAFE_INVENTORY1 = 200000.0;
    private static final double SAFE_INVENTORY2 = 100000.0;
    private String[] dates = new String[]{
            "2016-01-01", "2016-01-02", "2016-01-03", "2016-01-04", "2016-01-05", "2016-01-06", "2016-01-07", "2016-01-08", "2016-01-09", "2016-01-10", "2016-01-11", "2016-01-12", "2016-01-13", "2016-01-14", "2016-01-15", "2016-01-16", "2016-01-17", "2016-01-18", "2016-01-19", "2016-01-20", "2016-01-21", "2016-01-22", "2016-01-23", "2016-01-24", "2016-01-25", "2016-01-26", "2016-01-27", "2016-01-28", "2016-01-29", "2016-01-30", "2016-01-31",
            "2016-02-01", "2016-02-02", "2016-02-03", "2016-02-04", "2016-02-05", "2016-02-06", "2016-02-07", "2016-02-08", "2016-02-09", "2016-02-10", "2016-02-11", "2016-02-12", "2016-02-13", "2016-02-14", "2016-02-15", "2016-02-16", "2016-02-17", "2016-02-18", "2016-02-19", "2016-02-20", "2016-02-21", "2016-02-22", "2016-02-23", "2016-02-24", "2016-02-25", "2016-02-26", "2016-02-27", "2016-02-28", "2016-02-29",
            "2016-03-01", "2016-03-02", "2016-03-03", "2016-03-04", "2016-03-05", "2016-03-06", "2016-03-07", "2016-03-08", "2016-03-09", "2016-03-10", "2016-03-11", "2016-03-12", "2016-03-13", "2016-03-14", "2016-03-15", "2016-03-16", "2016-03-17", "2016-03-18", "2016-03-19", "2016-03-20", "2016-03-21", "2016-03-22", "2016-03-23", "2016-03-24", "2016-03-25", "2016-03-26", "2016-03-27", "2016-03-28", "2016-03-29", "2016-03-30", "2016-03-31",
            "2016-04-01", "2016-04-02", "2016-04-03", "2016-04-04", "2016-04-05", "2016-04-06", "2016-04-07", "2016-04-08", "2016-04-09", "2016-04-10", "2016-04-11", "2016-04-12", "2016-04-13", "2016-04-14", "2016-04-15", "2016-04-16", "2016-04-17", "2016-04-18", "2016-04-19", "2016-04-20", "2016-04-21", "2016-04-22", "2016-04-23", "2016-04-24", "2016-04-25", "2016-04-26", "2016-04-27", "2016-04-28", "2016-04-29", "2016-04-30",
            "2016-05-01", "2016-05-02", "2016-05-03", "2016-05-04", "2016-05-05", "2016-05-06", "2016-05-07", "2016-05-08", "2016-05-09", "2016-05-10", "2016-05-11", "2016-05-12", "2016-05-13", "2016-05-14", "2016-05-15", "2016-05-16", "2016-05-17", "2016-05-18", "2016-05-19", "2016-05-20", "2016-05-21", "2016-05-22", "2016-05-23", "2016-05-24", "2016-05-25", "2016-05-26", "2016-05-27", "2016-05-28", "2016-05-29", "2016-05-30", "2016-05-31",
            "2016-06-01", "2016-06-02", "2016-06-03", "2016-06-04", "2016-06-05", "2016-06-06", "2016-06-07", "2016-06-08", "2016-06-09", "2016-06-10", "2016-06-11", "2016-06-12", "2016-06-13", "2016-06-14", "2016-06-15", "2016-06-16", "2016-06-17", "2016-06-18", "2016-06-19", "2016-06-20", "2016-06-21", "2016-06-22", "2016-06-23", "2016-06-24", "2016-06-25", "2016-06-26", "2016-06-27", "2016-06-28", "2016-06-29", "2016-06-30",
            "2016-07-01", "2016-07-02", "2016-07-03", "2016-07-04", "2016-07-05", "2016-07-06", "2016-07-07", "2016-07-08", "2016-07-09", "2016-07-10", "2016-07-11", "2016-07-12", "2016-07-13", "2016-07-14", "2016-07-15", "2016-07-16", "2016-07-17", "2016-07-18", "2016-07-19", "2016-07-20", "2016-05-21", "2016-07-22", "2016-07-23", "2016-07-24", "2016-07-25", "2016-07-26", "2016-07-27", "2016-07-28", "2016-07-29", "2016-07-30", "2016-07-31",
            "2016-08-01", "2016-08-02", "2016-08-03", "2016-08-04", "2016-08-05", "2016-08-06", "2016-08-07", "2016-08-08", "2016-08-09", "2016-08-10", "2016-08-11", "2016-08-12", "2016-08-13", "2016-08-14", "2016-08-15", "2016-08-16", "2016-08-17", "2016-08-18", "2016-08-19", "2016-08-20", "2016-08-21", "2016-08-22", "2016-08-23", "2016-08-24", "2016-08-25", "2016-08-26", "2016-08-27", "2016-08-28", "2016-08-29", "2016-08-30", "2016-08-31",
            "2016-09-01", "2016-09-02", "2016-09-03", "2016-09-04", "2016-09-05", "2016-09-06", "2016-09-07", "2016-09-08", "2016-09-09", "2016-09-10", "2016-09-11", "2016-09-12", "2016-09-13", "2016-09-14", "2016-09-15", "2016-09-16", "2016-09-17", "2016-09-18", "2016-09-19", "2016-09-20", "2016-09-21", "2016-09-22", "2016-09-23", "2016-09-24", "2016-09-25", "2016-09-26", "2016-09-27", "2016-09-28", "2016-09-29", "2016-09-30",
            "2016-10-01", "2016-10-02", "2016-10-03", "2016-10-04", "2016-10-05", "2016-10-06", "2016-10-07", "2016-10-08", "2016-10-09", "2016-10-10", "2016-10-11", "2016-10-12", "2016-10-13", "2016-10-14", "2016-10-15", "2016-10-16", "2016-10-17", "2016-10-18", "2016-10-19", "2016-10-20", "2016-10-21", "2016-10-22", "2016-10-23", "2016-10-24", "2016-10-25", "2016-10-26", "2016-10-27", "2016-10-28", "2016-10-29", "2016-10-30", "2016-10-31",
            "2016-11-01", "2016-11-02", "2016-11-03", "2016-11-04", "2016-11-05", "2016-11-06", "2016-11-07", "2016-11-08", "2016-11-09", "2016-11-10", "2016-11-11", "2016-11-12", "2016-11-13", "2016-11-14", "2016-11-15", "2016-11-16", "2016-11-17", "2016-11-18", "2016-11-19", "2016-11-20", "2016-11-21", "2016-11-22", "2016-11-23", "2016-11-24", "2016-11-25", "2016-11-26", "2016-11-27", "2016-11-28", "2016-11-29", "2016-11-30",
            "2016-12-01", "2016-12-02", "2016-12-03", "2016-12-04", "2016-12-05", "2016-12-06", "2016-12-07", "2016-12-08", "2016-12-09", "2016-12-10", "2016-12-11", "2016-12-12", "2016-12-13", "2016-12-14", "2016-12-15", "2016-12-16", "2016-12-17", "2016-12-18", "2016-12-19", "2016-12-20", "2016-12-21", "2016-12-22", "2016-12-23", "2016-12-24", "2016-12-25", "2016-12-26", "2016-12-27", "2016-12-28", "2016-12-29", "2016-12-30", "2016-12-31"
    };

    @Override
    public double[] getFinancialWarningData(String phase) {
        double[] d1 = new double[]{-0.427388899, 1.15361991, 0, 1,72};
        double[] d2 = new double[]{0.016107802, 0.79333131, 0.42576151, 0.522988506,85};
        double[] d3 = new double[]{0.184210526, 0.762306242, 0, -0.5,93};
        double[] d4 = new double[]{0.118195526, 0.840758324, 0.502766899, 0.543956044,82};
        double[] d5 = new double[]{-0.135436666, 0.66250191, 0.365772675, 0.04040404,75};
        double[] d6 = new double[]{-0.135436666, 0.66250191, 0.365772675, 0.04040404,89};
        double[] d7 = new double[]{0.050197071, 0.764262341, 0.268813005, 0.034188034,80};
        double[] d8 = new double[]{0.384811104, 0.868068543, 0.218205806, 0.04,87};
        double[] d9 = new double[]{0.449995287, 0.893603908, 0.353359767, 0.294444444,95};
        double[] d10 = new double[]{0.046947311, 0.79006682, 0.208608445, -0.132653061,79};
        double[] d11 = new double[]{2.126696024, 1.546251191, 0.265654399, 0.031847134,96};
        double[] d12 = new double[]{0.114249139, 0.840758324, 0.439786248, -0.203592814,82};

        switch (phase) {
            case "2016-01":
                return d1;
            case "2016-02":
                return d2;
            case "2016-03":
                return d3;
            case "2016-04":
                return d4;
            case "2016-05":
                return d5;
            case "2016-06":
                return d6;
            case "2016-07":
                return d7;
            case "2016-08":
                return d8;
            case "2016-09":
                return d9;
            case "2016-10":
                return d10;
            case "2016-11":
                return d11;
            case "2016-12":
                return d12;
            default:
                return null;
        }
    }

    @Override
    public double[] getCashPoolData(String phase) {

        double[] d1 = new double[]{4000000,15500000,38500000};
        double[] d2 = new double[]{7000000,21000000,36000000};
        double[] d3 = new double[]{10000000,26500000,33500000};
        double[] d4 = new double[]{19000000,37000000,32000000};
        double[] d5 = new double[]{27000000,57350000,19650000};
        double[] d6 = new double[]{33000000,68350000,14650000};
        double[] d7 = new double[]{38000000,77350000,10650000};
        double[] d8 = new double[]{64000000,93350000,20650000};
        double[] d9 = new double[]{92400000,102350000,40050000};
        double[] d10 = new double[]{102400000,120350000,32050000};
        double[] d11 = new double[]{222400000,125350000,137050000};
        double[] d12 = new double[]{261400000,160350000,151050000};

        switch (phase) {
            case "2016-01":
                return d1;
            case "2016-02":
                return d2;
            case "2016-03":
                return d3;
            case "2016-04":
                return d4;
            case "2016-05":
                return d5;
            case "2016-06":
                return d6;
            case "2016-07":
                return d7;
            case "2016-08":
                return d8;
            case "2016-09":
                return d9;
            case "2016-10":
                return d10;
            case "2016-11":
                return d11;
            case "2016-12":
                return d12;
            default:
                return null;
        }

    }

    @Override
    public ArrayList<InventoryMonitorItemVo> getRawMaterialInventoryMonitorItem(String phase) throws ParseException {
        List<InventoryMonitorItemVo> list1 = new ArrayList<>();
        list1.add(new InventoryMonitorItemVo("a0", 100000, SAFE_INVENTORY1, "100%", "-"));
//        list1.add(new InventoryMonitorItemVo("a1",100000,SAFE_INVENTORY1,"-","0%"));
        list1.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list1.add(new InventoryMonitorItemVo("b1",0,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list2 = new ArrayList<>();
        list2.add(new InventoryMonitorItemVo("a0", 0, SAFE_INVENTORY1, "100%", "-"));
//        list2.add(new InventoryMonitorItemVo("a1",100000,SAFE_INVENTORY1,"-","0%"));
        list2.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list2.add(new InventoryMonitorItemVo("b1",0,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list3 = new ArrayList<>();
        list3.add(new InventoryMonitorItemVo("a0", 0, SAFE_INVENTORY1, "100%", "-"));
//        list3.add(new InventoryMonitorItemVo("a1",200000,SAFE_INVENTORY1,"-","0%"));
        list3.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list3.add(new InventoryMonitorItemVo("b1",0,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list4 = new ArrayList<>();
        list4.add(new InventoryMonitorItemVo("a0", 200000, SAFE_INVENTORY1, "100%", "-"));
//        list4.add(new InventoryMonitorItemVo("a1",200000,SAFE_INVENTORY1,"-","0%"));
        list4.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list4.add(new InventoryMonitorItemVo("b1",0,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list5 = new ArrayList<>();
        list5.add(new InventoryMonitorItemVo("a0", 100000, SAFE_INVENTORY1, "100%", "-"));
//        list5.add(new InventoryMonitorItemVo("a1",300000,SAFE_INVENTORY1,"-","0%"));
        list5.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list5.add(new InventoryMonitorItemVo("b1",0,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list6 = new ArrayList<>();
        list6.add(new InventoryMonitorItemVo("a0", 200000, SAFE_INVENTORY1, "100%", "-"));
//        list6.add(new InventoryMonitorItemVo("a1",200000,SAFE_INVENTORY1,"-","0%"));
        list6.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list6.add(new InventoryMonitorItemVo("b1",0,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list7 = new ArrayList<>();
        list7.add(new InventoryMonitorItemVo("a0", 300000, SAFE_INVENTORY1, "100%", "-"));
//        list7.add(new InventoryMonitorItemVo("a1",100000,SAFE_INVENTORY1,"-","0%"));
        list7.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list7.add(new InventoryMonitorItemVo("b1",100000,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list8 = new ArrayList<>();
        list8.add(new InventoryMonitorItemVo("a0", 100000, SAFE_INVENTORY1, "100%", "-"));
//        list8.add(new InventoryMonitorItemVo("a1",300000,SAFE_INVENTORY1,"-","0%"));
        list8.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list8.add(new InventoryMonitorItemVo("b1",0,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list9 = new ArrayList<>();
        list9.add(new InventoryMonitorItemVo("a0", 100000, SAFE_INVENTORY1, "100%", "-"));
//        list9.add(new InventoryMonitorItemVo("a1",100000,SAFE_INVENTORY1,"-","0%"));
        list9.add(new InventoryMonitorItemVo("b0", 100000, SAFE_INVENTORY2, "100%", "-"));
//        list9.add(new InventoryMonitorItemVo("b1",100000,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list10 = new ArrayList<>();
        list10.add(new InventoryMonitorItemVo("a0", 100000, SAFE_INVENTORY1, "100%", "-"));
//        list10.add(new InventoryMonitorItemVo("a1",300000,SAFE_INVENTORY1,"-","0%"));
        list10.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list10.add(new InventoryMonitorItemVo("b1",0,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list11 = new ArrayList<>();
        list11.add(new InventoryMonitorItemVo("a0", 100000, SAFE_INVENTORY1, "100%", "-"));
//        list11.add(new InventoryMonitorItemVo("a1",0,SAFE_INVENTORY1,"-","0%"));
        list11.add(new InventoryMonitorItemVo("b0", 200000, SAFE_INVENTORY2, "100%", "-"));
//        list11.add(new InventoryMonitorItemVo("b1",100000,SAFE_INVENTORY2,"-","0%"));

        List<InventoryMonitorItemVo> list12 = new ArrayList<>();
        list12.add(new InventoryMonitorItemVo("a0", 100000, SAFE_INVENTORY1, "100%", "-"));
//        list12.add(new InventoryMonitorItemVo("a1",0,SAFE_INVENTORY1,"-","0%"));
        list12.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list12.add(new InventoryMonitorItemVo("b1",200000,SAFE_INVENTORY2,"-","0%"));List<InventoryMonitorItemVo> list7 = new ArrayList<>();

        List<InventoryMonitorItemVo> list13 = new ArrayList<>();
        list13.add(new InventoryMonitorItemVo("a0", 100000, SAFE_INVENTORY1, "100%", "-"));
//        list13.add(new InventoryMonitorItemVo("a1",0,SAFE_INVENTORY1,"-","0%"));
        list13.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list13.add(new InventoryMonitorItemVo("b1",100000,SAFE_INVENTORY2,"-","0%"));List<InventoryMonitorItemVo> list7 = new ArrayList<>();

        List<InventoryMonitorItemVo> list14 = new ArrayList<>();
        list14.add(new InventoryMonitorItemVo("a0", 100000, SAFE_INVENTORY1, "100%", "-"));
//        list14.add(new InventoryMonitorItemVo("a1",0,SAFE_INVENTORY1,"-","0%"));
        list14.add(new InventoryMonitorItemVo("b0", 0, SAFE_INVENTORY2, "100%", "-"));
//        list14.add(new InventoryMonitorItemVo("b1",0,SAFE_INVENTORY2,"-","0%"));List<InventoryMonitorItemVo> list7 = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = sdf.parse(phase);
        if (date.after(sdf.parse("2016-01-01")) && date.before(sdf.parse("2016-01-04"))) {
            return (ArrayList<InventoryMonitorItemVo>) list1;
        } else if (date.after(sdf.parse("2016-01-05")) && date.before(sdf.parse("2016-01-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list2;
        } else if (date.after(sdf.parse("2016-02-01")) && date.before(sdf.parse("2016-02-02"))) {
            return (ArrayList<InventoryMonitorItemVo>) list3;
        } else if (date.after(sdf.parse("2016-02-03")) && date.before(sdf.parse("2016-03-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list4;
        } else if (date.after(sdf.parse("2016-04-01")) && date.before(sdf.parse("2016-04-30"))) {
            return (ArrayList<InventoryMonitorItemVo>) list5;
        } else if (date.after(sdf.parse("2016-05-01")) && date.before(sdf.parse("2016-05-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list6;
        } else if (date.after(sdf.parse("2016-06-01")) && date.before(sdf.parse("2016-06-30"))) {
            return (ArrayList<InventoryMonitorItemVo>) list7;
        } else if (date.after(sdf.parse("2016-07-01")) && date.before(sdf.parse("2016-07-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list8;
        } else if (date.after(sdf.parse("2016-08-01")) && date.before(sdf.parse("2016-08-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list9;
        } else if (date.after(sdf.parse("2016-09-01")) && date.before(sdf.parse("2016-09-30"))) {
            return (ArrayList<InventoryMonitorItemVo>) list10;
        } else if (date.after(sdf.parse("2016-10-01")) && date.before(sdf.parse("2016-10-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list11;
        } else if (date.after(sdf.parse("2016-11-01")) && date.before(sdf.parse("2016-11-30"))) {
            return (ArrayList<InventoryMonitorItemVo>) list12;
        } else if (date.after(sdf.parse("2016-12-01")) && date.before(sdf.parse("2016-12-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list13;
        } else {
            return (ArrayList<InventoryMonitorItemVo>) list14;
        }
    }

    @Override
    public ArrayList<InventoryMonitorItemVo> getProductInventoryMonitorItem(String phase) throws ParseException {
        List<InventoryMonitorItemVo> list1 = new ArrayList<>();
        list1.add(new InventoryMonitorItemVo("a1", 100000, SAFE_INVENTORY1, "-", "0%"));
        list1.add(new InventoryMonitorItemVo("b1", 0, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list2 = new ArrayList<>();
        list2.add(new InventoryMonitorItemVo("a1", 100000, SAFE_INVENTORY1, "-", "0%"));
        list2.add(new InventoryMonitorItemVo("b1", 0, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list3 = new ArrayList<>();
        list3.add(new InventoryMonitorItemVo("a1", 200000, SAFE_INVENTORY1, "-", "0%"));
        list3.add(new InventoryMonitorItemVo("b1", 0, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list4 = new ArrayList<>();
        list4.add(new InventoryMonitorItemVo("a1", 200000, SAFE_INVENTORY1, "-", "0%"));
        list4.add(new InventoryMonitorItemVo("b1", 0, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list5 = new ArrayList<>();
        list5.add(new InventoryMonitorItemVo("a1", 300000, SAFE_INVENTORY1, "-", "0%"));
        list5.add(new InventoryMonitorItemVo("b1", 0, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list6 = new ArrayList<>();
        list6.add(new InventoryMonitorItemVo("a1", 200000, SAFE_INVENTORY1, "-", "0%"));
        list6.add(new InventoryMonitorItemVo("b1", 0, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list7 = new ArrayList<>();
        list7.add(new InventoryMonitorItemVo("a1", 100000, SAFE_INVENTORY1, "-", "0%"));
        list7.add(new InventoryMonitorItemVo("b1", 100000, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list8 = new ArrayList<>();
        list8.add(new InventoryMonitorItemVo("a1", 300000, SAFE_INVENTORY1, "-", "0%"));
        list8.add(new InventoryMonitorItemVo("b1", 0, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list9 = new ArrayList<>();
        list9.add(new InventoryMonitorItemVo("a1", 100000, SAFE_INVENTORY1, "-", "0%"));
        list9.add(new InventoryMonitorItemVo("b1", 100000, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list10 = new ArrayList<>();
        list10.add(new InventoryMonitorItemVo("a1", 300000, SAFE_INVENTORY1, "-", "0%"));
        list10.add(new InventoryMonitorItemVo("b1", 0, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list11 = new ArrayList<>();
        list11.add(new InventoryMonitorItemVo("a1", 0, SAFE_INVENTORY1, "-", "0%"));
        list11.add(new InventoryMonitorItemVo("b1", 100000, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list12 = new ArrayList<>();
        list12.add(new InventoryMonitorItemVo("a1", 0, SAFE_INVENTORY1, "-", "0%"));
        list12.add(new InventoryMonitorItemVo("b1", 200000, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list13 = new ArrayList<>();
        list13.add(new InventoryMonitorItemVo("a1", 0, SAFE_INVENTORY1, "-", "0%"));
        list13.add(new InventoryMonitorItemVo("b1", 100000, SAFE_INVENTORY2, "-", "0%"));

        List<InventoryMonitorItemVo> list14 = new ArrayList<>();
        list14.add(new InventoryMonitorItemVo("a1", 0, SAFE_INVENTORY1, "-", "0%"));
        list14.add(new InventoryMonitorItemVo("b1", 0, SAFE_INVENTORY2, "-", "0%"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = sdf.parse(phase);

        if (date.after(sdf.parse("2016-01-01")) && date.before(sdf.parse("2016-01-04"))) {
            return (ArrayList<InventoryMonitorItemVo>) list1;
        } else if (date.after(sdf.parse("2016-01-05")) && date.before(sdf.parse("2016-01-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list2;
        } else if (date.after(sdf.parse("2016-02-01")) && date.before(sdf.parse("2016-02-02"))) {
            return (ArrayList<InventoryMonitorItemVo>) list3;
        } else if (date.after(sdf.parse("2016-02-03")) && date.before(sdf.parse("2016-03-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list4;
        } else if (date.after(sdf.parse("2016-04-01")) && date.before(sdf.parse("2016-04-30"))) {
            return (ArrayList<InventoryMonitorItemVo>) list5;
        } else if (date.after(sdf.parse("2016-05-01")) && date.before(sdf.parse("2016-05-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list6;
        } else if (date.after(sdf.parse("2016-06-01")) && date.before(sdf.parse("2016-06-30"))) {
            return (ArrayList<InventoryMonitorItemVo>) list7;
        } else if (date.after(sdf.parse("2016-07-01")) && date.before(sdf.parse("2016-07-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list8;
        } else if (date.after(sdf.parse("2016-08-01")) && date.before(sdf.parse("2016-08-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list9;
        } else if (date.after(sdf.parse("2016-09-01")) && date.before(sdf.parse("2016-09-30"))) {
            return (ArrayList<InventoryMonitorItemVo>) list10;
        } else if (date.after(sdf.parse("2016-10-01")) && date.before(sdf.parse("2016-10-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list11;
        } else if (date.after(sdf.parse("2016-11-01")) && date.before(sdf.parse("2016-11-30"))) {
            return (ArrayList<InventoryMonitorItemVo>) list12;
        } else if (date.after(sdf.parse("2016-12-01")) && date.before(sdf.parse("2016-12-31"))) {
            return (ArrayList<InventoryMonitorItemVo>) list13;
        } else {
            return (ArrayList<InventoryMonitorItemVo>) list14;
        }
    }

    @Override
    public ArrayList<SafeInventoryRateVo> getRawMaterialSafeInventoryRate(String phase) throws ParseException {

        List<SafeInventoryRateVo> list1 = new ArrayList<>();
        list1.add(new SafeInventoryRateVo("a0", 100000, 200000));
        list1.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list2 = new ArrayList<>();
        list2.add(new SafeInventoryRateVo("a0", 0, 200000));
        list2.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list3 = new ArrayList<>();
        list3.add(new SafeInventoryRateVo("a0", 0, 200000));
        list3.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list4 = new ArrayList<>();
        list4.add(new SafeInventoryRateVo("a0", 200000, 200000));
        list4.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list5 = new ArrayList<>();
        list5.add(new SafeInventoryRateVo("a0", 100000, 200000));
        list5.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list6 = new ArrayList<>();
        list6.add(new SafeInventoryRateVo("a0", 200000, 200000));
        list6.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list7 = new ArrayList<>();
        list7.add(new SafeInventoryRateVo("a0", 300000, 200000));
        list7.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list8 = new ArrayList<>();
        list8.add(new SafeInventoryRateVo("a0", 100000, 200000));
        list8.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list9 = new ArrayList<>();
        list9.add(new SafeInventoryRateVo("a0", 100000, 200000));
        list9.add(new SafeInventoryRateVo("b0", 100000, 100000));

        List<SafeInventoryRateVo> list10 = new ArrayList<>();
        list10.add(new SafeInventoryRateVo("a0", 100000, 200000));
        list10.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list11 = new ArrayList<>();
        list11.add(new SafeInventoryRateVo("a0", 100000, 200000));
        list11.add(new SafeInventoryRateVo("b0", 200000, 100000));

        List<SafeInventoryRateVo> list12 = new ArrayList<>();
        list12.add(new SafeInventoryRateVo("a0", 100000, 200000));
        list12.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list13 = new ArrayList<>();
        list13.add(new SafeInventoryRateVo("a0", 100000, 200000));
        list13.add(new SafeInventoryRateVo("b0", 0, 100000));

        List<SafeInventoryRateVo> list14 = new ArrayList<>();
        list14.add(new SafeInventoryRateVo("a0", 100000, 200000));
        list14.add(new SafeInventoryRateVo("b0", 0, 100000));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = sdf.parse(phase);
        if (date.after(sdf.parse("2016-01-01")) && date.before(sdf.parse("2016-01-04"))) {
            return (ArrayList<SafeInventoryRateVo>) list1;
        } else if (date.after(sdf.parse("2016-01-05")) && date.before(sdf.parse("2016-01-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list2;
        } else if (date.after(sdf.parse("2016-02-01")) && date.before(sdf.parse("2016-02-02"))) {
            return (ArrayList<SafeInventoryRateVo>) list3;
        } else if (date.after(sdf.parse("2016-02-03")) && date.before(sdf.parse("2016-03-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list4;
        } else if (date.after(sdf.parse("2016-04-01")) && date.before(sdf.parse("2016-04-30"))) {
            return (ArrayList<SafeInventoryRateVo>) list5;
        } else if (date.after(sdf.parse("2016-05-01")) && date.before(sdf.parse("2016-05-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list6;
        } else if (date.after(sdf.parse("2016-06-01")) && date.before(sdf.parse("2016-06-30"))) {
            return (ArrayList<SafeInventoryRateVo>) list7;
        } else if (date.after(sdf.parse("2016-07-01")) && date.before(sdf.parse("2016-07-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list8;
        } else if (date.after(sdf.parse("2016-08-01")) && date.before(sdf.parse("2016-08-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list9;
        } else if (date.after(sdf.parse("2016-09-01")) && date.before(sdf.parse("2016-09-30"))) {
            return (ArrayList<SafeInventoryRateVo>) list10;
        } else if (date.after(sdf.parse("2016-10-01")) && date.before(sdf.parse("2016-10-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list11;
        } else if (date.after(sdf.parse("2016-11-01")) && date.before(sdf.parse("2016-11-30"))) {
            return (ArrayList<SafeInventoryRateVo>) list12;
        } else if (date.after(sdf.parse("2016-12-01")) && date.before(sdf.parse("2016-12-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list13;
        } else {
            return (ArrayList<SafeInventoryRateVo>) list14;
        }
    }

    @Override
    public ArrayList<SafeInventoryRateVo> getProductSafeInventoryRate(String phase) throws ParseException {
        List<SafeInventoryRateVo> list1 = new ArrayList<>();
        list1.add(new SafeInventoryRateVo("a1", 100000, 200000));
        list1.add(new SafeInventoryRateVo("b1", 0, 100000));

        List<SafeInventoryRateVo> list2 = new ArrayList<>();
        list2.add(new SafeInventoryRateVo("a1", 100000, 200000));
        list2.add(new SafeInventoryRateVo("b1", 0, 100000));

        List<SafeInventoryRateVo> list3 = new ArrayList<>();
        list3.add(new SafeInventoryRateVo("a1", 200000, 200000));
        list3.add(new SafeInventoryRateVo("b1", 0, 100000));

        List<SafeInventoryRateVo> list4 = new ArrayList<>();
        list4.add(new SafeInventoryRateVo("a1", 200000, 200000));
        list4.add(new SafeInventoryRateVo("b1", 0, 100000));

        List<SafeInventoryRateVo> list5 = new ArrayList<>();
        list5.add(new SafeInventoryRateVo("a1", 300000, 200000));
        list5.add(new SafeInventoryRateVo("b1", 0, 100000));

        List<SafeInventoryRateVo> list6 = new ArrayList<>();
        list6.add(new SafeInventoryRateVo("a1", 200000, 200000));
        list6.add(new SafeInventoryRateVo("b1", 0, 100000));

        List<SafeInventoryRateVo> list7 = new ArrayList<>();
        list7.add(new SafeInventoryRateVo("a1", 100000, 200000));
        list7.add(new SafeInventoryRateVo("b1", 100000, 100000));

        List<SafeInventoryRateVo> list8 = new ArrayList<>();
        list8.add(new SafeInventoryRateVo("a1", 300000, 200000));
        list8.add(new SafeInventoryRateVo("b1", 0, 100000));

        List<SafeInventoryRateVo> list9 = new ArrayList<>();
        list9.add(new SafeInventoryRateVo("a1", 100000, 200000));
        list9.add(new SafeInventoryRateVo("b1", 100000, 100000));

        List<SafeInventoryRateVo> list10 = new ArrayList<>();
        list10.add(new SafeInventoryRateVo("a1", 300000, 200000));
        list10.add(new SafeInventoryRateVo("b1", 0, 100000));

        List<SafeInventoryRateVo> list11 = new ArrayList<>();
        list11.add(new SafeInventoryRateVo("a1", 0, 200000));
        list11.add(new SafeInventoryRateVo("b1", 100000, 100000));

        List<SafeInventoryRateVo> list12 = new ArrayList<>();
        list12.add(new SafeInventoryRateVo("a1", 0, 200000));
        list12.add(new SafeInventoryRateVo("b1", 200000, 100000));

        List<SafeInventoryRateVo> list13 = new ArrayList<>();
        list13.add(new SafeInventoryRateVo("a1", 0, 200000));
        list13.add(new SafeInventoryRateVo("b1", 100000, 100000));

        List<SafeInventoryRateVo> list14 = new ArrayList<>();
        list14.add(new SafeInventoryRateVo("a1", 0, 200000));
        list14.add(new SafeInventoryRateVo("b1", 0, 100000));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = sdf.parse(phase);
        if (date.after(sdf.parse("2016-01-01")) && date.before(sdf.parse("2016-01-04"))) {
            return (ArrayList<SafeInventoryRateVo>) list1;
        } else if (date.after(sdf.parse("2016-01-05")) && date.before(sdf.parse("2016-01-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list2;
        } else if (date.after(sdf.parse("2016-02-01")) && date.before(sdf.parse("2016-02-02"))) {
            return (ArrayList<SafeInventoryRateVo>) list3;
        } else if (date.after(sdf.parse("2016-02-03")) && date.before(sdf.parse("2016-03-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list4;
        } else if (date.after(sdf.parse("2016-04-01")) && date.before(sdf.parse("2016-04-30"))) {
            return (ArrayList<SafeInventoryRateVo>) list5;
        } else if (date.after(sdf.parse("2016-05-01")) && date.before(sdf.parse("2016-05-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list6;
        } else if (date.after(sdf.parse("2016-06-01")) && date.before(sdf.parse("2016-06-30"))) {
            return (ArrayList<SafeInventoryRateVo>) list7;
        } else if (date.after(sdf.parse("2016-07-01")) && date.before(sdf.parse("2016-07-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list8;
        } else if (date.after(sdf.parse("2016-08-01")) && date.before(sdf.parse("2016-08-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list9;
        } else if (date.after(sdf.parse("2016-09-01")) && date.before(sdf.parse("2016-09-30"))) {
            return (ArrayList<SafeInventoryRateVo>) list10;
        } else if (date.after(sdf.parse("2016-10-01")) && date.before(sdf.parse("2016-10-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list11;
        } else if (date.after(sdf.parse("2016-11-01")) && date.before(sdf.parse("2016-11-30"))) {
            return (ArrayList<SafeInventoryRateVo>) list12;
        } else if (date.after(sdf.parse("2016-12-01")) && date.before(sdf.parse("2016-12-31"))) {
            return (ArrayList<SafeInventoryRateVo>) list13;
        } else {
            return (ArrayList<SafeInventoryRateVo>) list14;
        }
    }

    @Override
    public ArrayList<InventoryChangeVo> getInventoryChange(String variety) {
        List<InventoryChangeVo> list1 = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            list1.add(new InventoryChangeVo(dates[i], 100000.0));
        }
        for (int i = 3; i <= 31; i++) {
            list1.add(new InventoryChangeVo(dates[i], 0.0));
        }
        for (int i = 32; i <= 89; i++) {
            list1.add(new InventoryChangeVo(dates[i], 200000.0));
        }
        for (int i = 90; i <= 119; i++) {
            list1.add(new InventoryChangeVo(dates[i], 100000.0));
        }
        for (int i = 120; i <= 150; i++) {
            list1.add(new InventoryChangeVo(dates[i], 200000.0));
        }
        for (int i = 151; i <= 180; i++) {
            list1.add(new InventoryChangeVo(dates[i], 300000.0));
        }
        for (int i = 181; i <= 365; i++) {
            list1.add(new InventoryChangeVo(dates[i], 100000.0));
        }

        List<InventoryChangeVo> list2 = new ArrayList<>();
        for (int i = 0; i <= 29; i++) {
            list2.add(new InventoryChangeVo(dates[i], 100000.0));
        }
        for (int i = 30; i <= 89; i++) {
            list2.add(new InventoryChangeVo(dates[i], 200000.0));
        }
        for (int i = 90; i <= 119; i++) {
            list2.add(new InventoryChangeVo(dates[i], 300000.0));
        }
        for (int i = 120; i <= 150; i++) {
            list2.add(new InventoryChangeVo(dates[i], 200000.0));
        }
        for (int i = 151; i <= 180; i++) {
            list2.add(new InventoryChangeVo(dates[i], 100000.0));
        }
        for (int i = 181; i <= 211; i++) {
            list2.add(new InventoryChangeVo(dates[i], 300000.0));
        }
        for (int i = 212; i <= 242; i++) {
            list2.add(new InventoryChangeVo(dates[i], 100000.0));
        }
        for (int i = 243; i <= 272; i++) {
            list2.add(new InventoryChangeVo(dates[i], 300000.0));
        }
        for (int i = 273; i <= 365; i++) {
            list2.add(new InventoryChangeVo(dates[i], 0.0));
        }

        List<InventoryChangeVo> list3 = new ArrayList<>();
        for (int i = 0; i <= 212; i++) {
            list3.add(new InventoryChangeVo(dates[i], 0));
        }
        for (int i = 213; i <= 272; i++) {
            list3.add(new InventoryChangeVo(dates[i], 100000.0));
        }
        for (int i = 273; i <= 303; i++) {
            list3.add(new InventoryChangeVo(dates[i], 200000.0));
        }
        for (int i = 304; i <= 365; i++) {
            list3.add(new InventoryChangeVo(dates[i], 0.0));
        }

        List<InventoryChangeVo> list4 = new ArrayList<>();
        for (int i = 0; i <= 150; i++) {
            list4.add(new InventoryChangeVo(dates[i], 0.0));
        }
        for (int i = 151; i <= 180; i++) {
            list4.add(new InventoryChangeVo(dates[i], 100000.0));
        }
        for (int i = 181; i <= 211; i++) {
            list4.add(new InventoryChangeVo(dates[i], 0.0));
        }
        for (int i = 212; i <= 242; i++) {
            list4.add(new InventoryChangeVo(dates[i], 100000.0));
        }
        for (int i = 243; i <= 272; i++) {
            list4.add(new InventoryChangeVo(dates[i], 0.0));
        }
        for (int i = 273; i <= 303; i++) {
            list4.add(new InventoryChangeVo(dates[i], 100000.0));
        }
        for (int i = 304; i <= 333; i++) {
            list4.add(new InventoryChangeVo(dates[i], 200000.0));
        }
        for (int i = 334; i <= 365; i++) {
            list4.add(new InventoryChangeVo(dates[i], 100000.0));
        }

        switch (variety) {
            case "a0":
                return (ArrayList<InventoryChangeVo>) list1;
            case "a1":
                return (ArrayList<InventoryChangeVo>) list2;
            case "b0":
                return (ArrayList<InventoryChangeVo>) list3;
            case "b1":
                return (ArrayList<InventoryChangeVo>) list4;
            default:
                return null;
        }
    }

    @Override
    public ArrayList<PunctualDeliveryRateChangeVo> getPunctualDeliveryRateChange(String variety) {
        List<PunctualDeliveryRateChangeVo> list1 = new ArrayList<>();
        List<PunctualDeliveryRateChangeVo> list2 = new ArrayList<>();

        for (int i = 0; i <= 365; i++) {
            list1.add(new PunctualDeliveryRateChangeVo(dates[i], 100));
        }


        for (int i = 0; i <= 211; i++) {
            list2.add(new PunctualDeliveryRateChangeVo(dates[i], 100));
        }
        for (int i = 212; i <= 272; i++) {
            list2.add(new PunctualDeliveryRateChangeVo(dates[i], 50));
        }
        for (int i = 273; i <= 365; i++) {
            list2.add(new PunctualDeliveryRateChangeVo(dates[i], 33.3));
        }

        switch (variety) {
            case "a0":
                return (ArrayList<PunctualDeliveryRateChangeVo>) list1;
            case "b0":
                return (ArrayList<PunctualDeliveryRateChangeVo>) list2;
            default:
                return null;
        }
    }

    @Override
    public ArrayList<RefundRateChangeVo> getRefundRateChange(String variety) {
        List<RefundRateChangeVo> list1 = new ArrayList<>();
        List<RefundRateChangeVo> list2 = new ArrayList<>();

        for (int i = 0; i <= 365; i++) {
            list1.add(new RefundRateChangeVo(dates[i], 0));
        }
        for (int i = 0; i <= 365; i++) {
            list2.add(new RefundRateChangeVo(dates[i], 0));
        }

        switch (variety) {
            case "a1":
                return (ArrayList<RefundRateChangeVo>) list1;
            case "b1":
                return (ArrayList<RefundRateChangeVo>) list2;
            default:
                return null;
        }
    }

    @Override
    public List<SupplyModuleOne> getSupplyChain1Data() {
        List<SupplyModuleOne> list = new ArrayList<>();

        double[] d1 = new double[]{0.08368, 0};
        double[] d2 = new double[]{0, 0};
        double[] d3 = new double[]{0, 0};
        double[] d4 = new double[]{0.275, 3.730769};
        SupplyModuleOne module1 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{0.03141, 0.4};
        d2 = new double[]{0.078524, 0.4};
        d3 = new double[]{0, 3};
        d4 = new double[]{0.324425, 2.434599};
        SupplyModuleOne module2 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{0, 0};
        d2 = new double[]{0, 0};
        d3 = new double[]{-1, -1};
        d4 = new double[]{0.324425, 2.329114};
        SupplyModuleOne module3 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{0.056778, 0.4};
        d2 = new double[]{0.141945, 0.615385};
        d3 = new double[]{0, 0};
        d4 = new double[]{0.326988, 2.530249};
        SupplyModuleOne module4 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{0.51488, 0.4};
        d2 = new double[]{0.128721, 0.55814};
        d3 = new double[]{0, 0};
        d4 = new double[]{0.346166, 2.241473};
        SupplyModuleOne module5 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{0.58703, 0.5};
        d2 = new double[]{0.117405, 0.434783};
        d3 = new double[]{0, 0.25};
        d4 = new double[]{0.01124, 2.510612};
        SupplyModuleOne module6 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{0.04261, 0.4};
        d2 = new double[]{0.106525, 0.53333};
        d3 = new double[]{0, -0.2};
        d4 = new double[]{0.370118, 2.244153};
        SupplyModuleOne module7 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{0.048626, 0.5};
        d2 = new double[]{0.097253, 0.4};
        d3 = new double[]{0, 0.25};
        d4 = new double[]{0.365751, 2.305099};
        SupplyModuleOne module8 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{0.053004, 0.4};
        d2 = new double[]{0.132509, 0.734694};
        d3 = new double[]{0.5, 0.2};
        d4 = new double[]{0.387372, 2.254284};
        SupplyModuleOne module9 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{0.040543, 0.5};
        d2 = new double[]{0.081087, 0.434783};
        d3 = new double[]{-0.3333, -0.1667};
        d4 = new double[]{0.380304, 2.355599};
        SupplyModuleOne module10 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{0.039596, 0.5};
        d2 = new double[]{0.079192, 0.55556};
        d3 = new double[]{0, 0};
        d4 = new double[]{0.336643, 2.907336};
        SupplyModuleOne module11 = new SupplyModuleOne(d1, d2, d3, d4);

        d1 = new double[]{-0.00419, 0};
        d2 = new double[]{0, 0};
        d3 = new double[]{-1, -1.1};
        d4 = new double[]{0.264673, 4.081761};
        SupplyModuleOne module12 = new SupplyModuleOne(d1, d2, d3, d4);

        list.add(module1);
        list.add(module2);
        list.add(module3);
        list.add(module4);
        list.add(module5);
        list.add(module6);
        list.add(module7);
        list.add(module8);
        list.add(module9);
        list.add(module10);
        list.add(module11);
        list.add(module12);
        return list;
    }

    @Override
    public List<double[]> getSupplyChain3Date() {
        List<double[]> list = new ArrayList<>();

//        double[] d1 = new double[]{0.010755509, 5.128205128, 0.540005247, 0, 0, 1, 0.427350427, 0, 1, 0};
//        double[] d2 = new double[]{0.034402358, 5.530973451, 0.428577713, 0, 1, 0.33333, 1.477272727, 1, 0.4, 8.536585366};
//        double[] d3 = new double[]{0.018966236, 1.13960114, 0.575760734, 0, 0, 0, 1.01010101, 0, 0, -0.767263427};
//        double[] d4 = new double[]{0.042705764, 14.7766323, 0.455977284, 0, 1, 0.33333, 5.227272727, 1, 0.244186047, 5.164835165};
//        double[] d5 = new double[]{0.037462275, 95.69892473, 0.49366881, 0, 1, 0.33333, 2.857142857, 1, 0.269662921, 0.017825312};
//        double[] d6 = new double[]{0.01259167, 1.13960114, 0.659817352, 0, 0, 1, 0.649350649, 0, 1, -0.840630473};
//        double[] d7 = new double[]{0.025227927, 6.097037355, 0.520094783, 0, 0.75, 0, 1.544715447, 0.75, 0, 4.065934066};
//        double[] d8 = new double[]{0.021230368, 3.686200378, 0.665963126, 0, 1, 0, 0.693641618, 1, 0, -0.629067245};
//        double[] d9 = new double[]{0.032676556, 5.475663717, 0.507587194, 0, 0.833333, 0.5, 1.777777778, 0.833333, 0.242424242, 2.923976608};
//        double[] d10 = new double[]{0.009299762, 0.854700855, 0.673647229, 0, 0, 1, 0.407239819, 0, 1, -0.87928465};
//        double[] d11 = new double[]{0.01504821, -0.228021282, 0.456871087, 0, 0, 0, 0.511363636, 0, 0, 0};
//        double[] d12 = new double[]{0.017086956, 0.630252101, 0.454002291, 0, 0, 0, 0.763358779, 0, 0, 0.12345679};


        double[] d1 = new double[]{0.010755509, 0.034402358, 0.018966236, 0.042705764, 0.037462275, 0.01259167, 0.025227927, 0.021230368, 0.032676556, 0.009299762, 0.01504821, 0.017086956};
        double[] d2 = new double[]{5.128205128, 5.530973451, 1.13960114, 14.7766323, 95.69892473, 1.13960114, 6.097037355, 3.686200378, 5.475663717, 0.854700855, -0.228021282, 0.630252101};
        double[] d3 = new double[]{0.540005247, 0.428577713, 0.575760734, 0.455977284, 0.49366881, 0.659817352, 0.520094783, 0.665963126, 0.507587194, 0.673647229, 0.456871087, 0.454002291};
        double[] d4 = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] d5 = new double[]{0, 1, 0, 1, 1, 0, 0.75, 1, 0.833333, 1, 0, 0};
        double[] d6 = new double[]{1, 0.33333, 0, 0.3333, 0.3333, 1, 0, 0, 0.5, 1, 0, 0};
        double[] d7 = new double[]{0.427350427, 1.477272727, 1.01010101, 5.227272727, 2.857142857, 0.649350649, 1.544715447, 0.693641618, 1.777777778, 0.407239819, 0.511363636, 0.763358779};
        double[] d8 = new double[]{0, 1, 0, 1, 1, 0, 0.75, 1, 0.833333, 0, 0, 0};
        double[] d9 = new double[]{1, 0.4, 0, 0.244186047, 0.269662921, 1, 0, 0, 0.242424242, 1, 0, 0};
        double[] d10 = new double[]{0, 8.536585366, -0.767263427, 5.164835165, 0.017825312, -0.840630473, 4.065934066, -0.629067245, 2.923976608, -0.87928465, 0, 0.12345679};

        System.out.println(d1.length + " " + d2.length + " " + d3.length + " " + d4.length + " " + d5.length + " " + d6.length + " " + d7.length + " " + d8.length + " " + d9.length + " " + d10.length + " ");
        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        list.add(d5);
        list.add(d6);
        list.add(d7);
        list.add(d8);
        list.add(d9);
        list.add(d10);
        return list;
    }

    @Override
    public List<double[]> getSupplyChain2Data1() {
        List<double[]> list = new ArrayList<>();

        double[] d1 = new double[]{0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0};
        double[] d2 = new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
        double[] d3 = new double[]{0, 1, 0, 1, 1, 0, 0.5, 0, 0.5, 0, 0, 0};
        double[] d4 = new double[]{0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0};
        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        return list;
    }

    @Override
    public List<double[]> getSupplyChain2Data2() {
        List<double[]> list = new ArrayList<>();

        double[] d1 = new double[]{0, 0.48, 0, 0.48, 0.48, 0, 0, 0.48, 0, 0, 0, 0};
        double[] d2 = new double[]{0, 0, 0, 0, 0.66, 0, 0.66, 0, 0.66, 0, 0, 0};
        double[] d3 = new double[]{0, 0.67, 0, 0.67, 0.67, 0, 0.67, 0, 0.67, 0, 0, 0};
        double[] d4 = new double[]{0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0};
        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        return list;
    }

    @Override
    public List<double[]> getSupplyChain2Data3() {
        List<double[]> list = new ArrayList<>();

        double[] d1 = new double[]{16.66666667, -57, 15, -38, 0, 10, 10, -12.66666667, 0, 0, 0, 0};
        double[] d2 = new double[]{45, 0, 0, 0, -14.5, 45, -58, 0, 0, 0, 0, 0};
        double[] d3 = new double[]{0.014285714, 0.05, 0.005882353, 0.1, -0.01, 0.1, 0, -0.04, 0, 0, 0, 0};
        double[] d4 = new double[]{0, 0, 0, 0, 0.011904762, 0, 0.071428571, 0, 0.013513514, -0.018867925, 0, 0};

        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);

        return list;
    }
}

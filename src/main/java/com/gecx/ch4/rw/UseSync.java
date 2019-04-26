package com.gecx.ch4.rw;

import com.gecx.utils.SleepTools;

/**
 * @author Gecx
 * @Description: TODO
 * @date 2019/4/26 11:15
 */
public class UseSync implements GoodsService {

    private GoodsInfo goodsInfo;

    public UseSync(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public synchronized GoodsInfo getNum() {
        SleepTools.ms(5);
        return goodsInfo;
    }

    @Override
    public void setNum(int number) {
        SleepTools.ms(5);
        goodsInfo.changeNumber(number);
    }
}

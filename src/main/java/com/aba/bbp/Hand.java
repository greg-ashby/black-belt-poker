package com.aba.bbp;

import com.aba.bbp.enums.CardRank;
import com.aba.bbp.enums.HandRank;

import java.util.List;

public class Hand {
    public HandRank handRank;
    public List<CardRank> kickers;

    public Hand() {
    }

    public Hand(HandRank handRank, List<CardRank> kickers) {
        this.handRank = handRank;
        this.kickers = kickers;
    }
}

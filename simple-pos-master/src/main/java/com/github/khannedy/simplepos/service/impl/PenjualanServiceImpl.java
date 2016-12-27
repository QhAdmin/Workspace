/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khannedy.simplepos.service.impl;

import com.github.khannedy.simplepos.entity.Pengaturan;
import com.github.khannedy.simplepos.entity.master.DetailBarang;
import com.github.khannedy.simplepos.entity.report.Jurnal;
import com.github.khannedy.simplepos.entity.transaction.DetailPenjualan;
import com.github.khannedy.simplepos.entity.transaction.Penjualan;
import com.github.khannedy.simplepos.service.JurnalService;
import com.github.khannedy.simplepos.service.PengaturanService;
import com.github.khannedy.simplepos.service.PenjualanService;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author echo
 */
@Service
public class PenjualanServiceImpl implements PenjualanService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PengaturanService pengaturanService;

    @Autowired
    private JurnalService jurnalService;

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional
    public void save(Penjualan penjualan) {
        Session session = currentSession();
        session.save(penjualan);
        for (DetailPenjualan detailPenjualan : penjualan.getDaftarPenjualan()) {
            DetailBarang detailBarang = detailPenjualan.getDetailBarang();
            detailBarang.setStok(detailBarang.getStok() - detailPenjualan.getJumlah());
            session.update(detailBarang);
        }

        Pengaturan pengaturan = pengaturanService.find("saldo-terakhir");
        long saldo = pengaturan.getNilaiLong() + penjualan.getTotal();

        Jurnal jurnal = new Jurnal();
        jurnal.setDebit(penjualan.getTotal());
        jurnal.setKredit(0L);
        jurnal.setNama("Penjualan : No " + penjualan.getId() + " Kepada " + penjualan.getPelanggan().getNama());
        jurnal.setSaldo(saldo);
        jurnal.setSaldoSebelumnya(pengaturan.getNilaiLong());
        jurnal.setWaktu(penjualan.getWaktuTransaksi());

        pengaturan.setNilaiLong(saldo);
        pengaturanService.update(pengaturan);

        jurnalService.save(jurnal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Penjualan> findAll(Date from, Date to) {
        Session session = currentSession();
        return session.createQuery("select a from Penjualan a where date(a.waktuTransaksi) between date(:from) and date(:to)").
                setDate("from", from).setDate("to", to).list();
    }
}

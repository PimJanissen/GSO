package bank.bankieren;

import fontyspublisher.IRemotePublisherForListener;
import java.io.Serializable;

public interface IRekening extends IRemotePublisherForListener, Serializable {
  int getNr();
  Money getSaldo();
  IKlant getEigenaar();
  int getKredietLimietInCenten();
}


package dmitry_karasik.demo.repository;

import dmitry_karasik.demo.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}

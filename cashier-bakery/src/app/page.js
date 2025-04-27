// app/page.js home page
'use client';
import { useRouter } from 'next/navigation';
import './welcome.css';
import Image from 'next/image';


export default function HomePage() {
  const router = useRouter();

  const goToBakeryItems = () => {
    router.push('/bakery-items');
    //later this button will  changed
  };

  return (
    // <main style={{ textAlign: 'center', marginTop: '3rem' }}>
    //   <h2>test1</h2>
    //   <h2>test2</h2>
    //   <h2>test3</h2>

    //   <h1>Welcome to the Cashier Bakery System</h1>
    //   <p>Manage orders, view inventory, and more.</p>
    //   <button
    //     onClick={goToBakeryItems}
    //     style={{
    //       padding: '10px 20px',
    //       fontSize: '16px',
    //       cursor: 'pointer',
    //       marginTop: '20px'
    //     }}
    //   >
    //     View Bakery Items
    //   </button>
    // </main>

    <main>
            <div className="header">
        {/* <img id="logo" src="assets/cake.png" />
             */}
        <Image id="logo"
          src="/cake.png"
          width={50}
          height={50}
          alt="Picture of the logo"
        />
        <h1 className="header-text">Cashier Bakery System</h1>
      </div>

      <div className="nav-bar">
        <ul>
          <li><a href="./">Home</a></li>
          <li><a href="bakery-items">Products</a></li>
          <li><a href="orders">Orders</a></li>
        </ul>
      </div>


      <div className="welcome">
        <h1>Welcome to the Cashier Bakery System</h1>
        <p>Manage orders, view inventory, and more.</p>
        <button
          onClick={goToBakeryItems}
          style={{
            padding: '10px 20px',
            fontSize: '16px',
            cursor: 'pointer',
            marginTop: '20px'
          }}
        >
          View Products
        </button>
      </div>
    </main >
  );
}

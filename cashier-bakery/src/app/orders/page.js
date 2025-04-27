'use client';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import './orders.css';
import Image from 'next/image';

export default function OrdersPage() {
  const [items, setItems] = useState([]);

  //dummy data, list sql stuff later
  useEffect(() => {
    //simulate data fetch, but later acc fetch from msyql
    const dummyOrderData = [
      { OrderID: 1, CustomerID: 101, EmployeeID: 301, DatePlaced: "2025-04-26", TotalPrice: 17.50 },
      { OrderID: 2, CustomerID: 102, EmployeeID: 302, DatePlaced: "2012-03-02", TotalPrice: 13.50 },
      { OrderID: 3, CustomerID: 103, EmployeeID: 303, DatePlaced: "2019-02-26", TotalPrice: 11.50 },
    ];
    setItems(dummyOrderData);
  }, []);

  return (
    // <main style={{ textAlign: 'center', marginTop: '3rem' }}>
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

      <div className="menu">

        {items.map(item => (
          <div key= {item.OrderID} className = "order">

          {/* <Image
            src= {`imglink`}
            width={200}
            height={200}
            alt="Picture of the logo"/> */}
              {/* <img
                key={item.id}
                src={item.imglink}
                style={{ width: 20, height: 20}}
              /> */}

            <p>Order #{item.OrderID}</p>
            <p>Customer #{item.CustomerID}</p>
            <p>Employee #{item.EmployeeID}</p>
            <p>Date Placed: {item.DatePlaced}</p>
            <p>Order Total: ${item.TotalPrice}</p>

          </div>
        ))}
        
        </div>


    </main>
  );
}







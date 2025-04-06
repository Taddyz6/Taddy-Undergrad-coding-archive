import React, { useEffect, useState } from 'react'

interface DataItem {
    Value: number;
    TimePeriod: string;
}

const Debug = () => {
    const [data, setData] = useState<DataItem[]>([]);
    useEffect(() => {
        fetch('http://localhost:5001/data')
        .then(res => res.json())
        .then((data: DataItem[]) => setData(data))
        .catch(err => console.log(err))
    },[])
    return (
        <table>
            <thead>
                <th>Value</th>
                <th>Time Period</th>
            </thead>
            <tbody>
                {data.map((d: DataItem, i: number) => (
                    <tr key = {i}>
                        <td>{d.Value}</td>
                        <td>{d.TimePeriod}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    )
}
export default Debug
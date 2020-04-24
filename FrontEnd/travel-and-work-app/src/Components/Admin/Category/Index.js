import React from "react";
import { Table } from "react-bootstrap";
import { Container } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Spinner from "react-bootstrap/Spinner";
import { Link } from "react-router-dom";
import ButtonInRow from "../../SmartGadgets/ButtonInRow";

class Index extends React.Component {
    state = {
        categories: null,
    };

    async componentDidMount() {
        const response = await fetch(`http://localhost:8080/category`);
        const data = await response.json();
        console.log(data);
        this.setState({ categories: data });
    }

    render() {
        if (this.state.categories === null) {
            return (
                <Container className="mt-5 p-5">
                    <Spinner animation="border" role="status">
                        <span className="sr-only">Loading...</span>
                    </Spinner>
                </Container>
            );
        } else {
            let tableRows = [];
            if (this.state.categories.length > 0) {
                this.state.categories.forEach((element) => {
                    tableRows.push(
                        <tr>
                            <td>{element.name}</td>
                            <td>
                                <Link
                                    className="p-3"
                                    to={"category/" + element.id + "/edit"}
                                >
                                    <FontAwesomeIcon icon="cog" />
                                </Link>

                                <Link className="p-3">
                                    <FontAwesomeIcon icon="trash-alt" />
                                </Link>
                            </td>
                        </tr>
                    );
                });
            }

            return (
                <Container>
                    <ButtonInRow
                        variant="success"
                        link="/category/create"
                        side="right"
                        label="Add category"
                    />

                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Settings</th>
                            </tr>
                        </thead>
                        <tbody>{tableRows}</tbody>
                    </Table>
                </Container>
            );
        }
    }
}

export default Index;
